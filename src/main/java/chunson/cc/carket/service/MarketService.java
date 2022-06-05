package chunson.cc.carket.service;

import chunson.cc.carket.Exception.AssetNotFoundException;
import chunson.cc.carket.Exception.NotEnoughMoneyException;
import chunson.cc.carket.Exception.TransferVNTException;
import chunson.cc.carket.mapper.AssetMapper;
import chunson.cc.carket.mapper.EventMapper;
import chunson.cc.carket.mapper.MarketMapper;
import chunson.cc.carket.mapper.UserMapper;
import chunson.cc.carket.model.*;
import chunson.cc.carket.utils.VNTUtils;
import com.google.gson.Gson;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MarketService
{
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final VNTUtils vntUtils;
    private final AssetMapper assetMapper;
    private final MarketMapper mapper;
    private final AccountService accountService;
    private final AssetService assetService;

    private final static String PLATFORM_ADDRESS = "0xcda3e97ba22c1a3a2b3cc710c18d7270591f8e49";
    private final static double PLATFORM_RATE = 0.025;

    public MarketService(EventMapper eventMapper, UserMapper userMapper, VNTUtils vntUtils, AssetMapper assetMapper, MarketMapper mapper, AccountService accountService, AssetService assetService)
    {
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
        this.vntUtils = vntUtils;
        this.assetMapper = assetMapper;
        this.mapper = mapper;
        this.accountService = accountService;
        this.assetService = assetService;
    }

    public List<Map<String, Object>> getAll(@NotNull int page, int num)
    {
        List<ShowAsset> assets = mapper.selectAllAsset((page - 1) * num, num);
        List<Map<String, Object>> maps = new ArrayList<>();
        if (assets != null)
        {
            for (ShowAsset asset : assets)
            {
                if (asset.getCreator() != null)
                {
                    User creator = userMapper.getUserByAddress(asset.getCreator());
                    asset.setCreatorName(creator.getUsername());
                    asset.setCreatorAvatarRoute(creator.getAvatarRoute());
                }

                if (asset.getOwner() != null)
                {
                    User owner = userMapper.getUserByAddress(asset.getOwner());
                    asset.setOwnerName(owner.getUsername());
                    asset.setOwnerAvatarRoute(owner.getAvatarRoute());
                }
            }
            for (ShowAsset obj : assets)
            {
                maps.add(obj.marketing());
            }
        }
        return maps;
    }

    public boolean buy(@NotNull String address, long aid) throws AssetNotFoundException, NotEnoughMoneyException, TransferVNTException
    {
        Asset asset = assetMapper.getAssetByAid(aid);
        if (asset == null)
        {
            throw new AssetNotFoundException();
        }

        double balance = accountService.getBalance(address);
        double price = asset.getPrice();
        if (balance <= price)
        {
            throw new NotEnoughMoneyException();
        }

        long tokenId = asset.getTokenId();
        int rate = asset.getRate();
        String creator = asset.getCreator();
        String owner = asset.getOwner();

        BigDecimal myPrice = new BigDecimal(price + "");
        BigDecimal platformRate = new BigDecimal(PLATFORM_RATE + "");
        BigDecimal platformAmount = myPrice.multiply(platformRate);

        BigDecimal creatorAmount = new BigDecimal(0 + "");
        if (rate != 0 && !creator.equals(owner))
        {
            creatorAmount = myPrice.subtract(platformAmount).multiply(BigDecimal.valueOf(rate * 0.01));
        }

        BigDecimal ownerAmount = myPrice.subtract(creatorAmount).subtract(platformAmount);

        System.out.println("myPrice: " + myPrice.doubleValue());
        System.out.println("rate: " + rate);
        System.out.println("platform: " + platformAmount.doubleValue());
        System.out.println("creator: " + creatorAmount.doubleValue());
        System.out.println("owner: " + ownerAmount.doubleValue());

        String platformHash = transferVNT(PLATFORM_ADDRESS, address, platformAmount.doubleValue());
        Event platformEvent = new Event("交易手续费", address, PLATFORM_ADDRESS, tokenId, platformAmount.doubleValue(), platformHash);
        eventMapper.insertEventWithTxHash(platformEvent);

        String ownerHash = transferVNT(owner, address, ownerAmount.doubleValue());
        Event ownerEvent = new Event("交易转账", address, owner, tokenId, ownerAmount.doubleValue(), ownerHash);
        eventMapper.insertEventWithTxHash(ownerEvent);

        if (rate != 0 && !creator.equals(owner))
        {
            String creatorHash = transferVNT(creator, address, creatorAmount.doubleValue());
            Event creatorEvent = new Event("交易作者费", address, creator, tokenId, creatorAmount.doubleValue(), creatorHash);
            eventMapper.insertEventWithTxHash(creatorEvent);
        }

        return assetService.transfer(owner, address, aid);
    }

    private String transferVNT(String to, String from, double amount) throws TransferVNTException
    {
        String transferJson = vntUtils.transferVNT(to, from, amount);
        TxResult txResult = new Gson().fromJson(transferJson, TxResult.class);
        String txHash = txResult.getTxHash();
        String txJson = vntUtils.getReceipt(txHash);
        TxReceipt receipt = new Gson().fromJson(txJson, TxReceipt.class);
        System.out.println(receipt.getStatus());
        if (receipt.getStatus().equals("0x0"))
        {
            throw new TransferVNTException();
        }
        return txHash;
    }
}