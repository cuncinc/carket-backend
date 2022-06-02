package chunson.cc.carket.service;

import chunson.cc.carket.Exception.CreatorNotCorrespondException;
import chunson.cc.carket.Exception.StateNotCorrespondException;
import chunson.cc.carket.mapper.AssetMapper;
import chunson.cc.carket.mapper.EventMapper;
import chunson.cc.carket.mapper.FavoriteMapper;
import chunson.cc.carket.mapper.UserMapper;
import chunson.cc.carket.model.*;
import chunson.cc.carket.utils.FileUtils;
import chunson.cc.carket.utils.VNTUtils;
import chunson.cc.carket.utils.IpfsUtils;
import com.google.gson.Gson;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AssetService
{
    private final AssetMapper assetMapper;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final VNTUtils vntUtils;

    public AssetService(AssetMapper assetMapper, EventMapper eventMapper, UserMapper userMapper, VNTUtils vntUtils)
    {
        this.assetMapper = assetMapper;
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
        this.vntUtils = vntUtils;
    }

    public Asset getAssetByAid(Long aid)
    {
        return assetMapper.getAssetByAid(aid);
    }

    public List<Map<String, String>> getAssets(@NotNull int page, int num)
    {
        List<ShowAsset> assets = assetMapper.selectAssets((page - 1) * num, num);
        List<Map<String, String>> maps = new ArrayList<>();
        for (ShowAsset obj : assets)
        {
            maps.add(obj.marketing());
        }
        return maps;
    }

    public Asset getOneAssetByAid(@NotNull Long aid)
    {
        Asset asset = assetMapper.selectOneAssetByAid(aid);
        if (null == asset) return null;
        return asset;
    }

    public Asset getOneAssetByTokenId(@NotNull Long tokenId)
    {
        //todo 添加创作者、所有者信息
        Asset asset = assetMapper.selectOneAssetByTokenId(tokenId);
        return asset;
    }

    public List<Map<String, String>> getAssetsOfUser(@NotNull String whose, @NotNull String which, @NotNull boolean isMe)
    {
        List<ShowAsset> assets = null;
        if (which.equals("owned"))
        {
            assets = assetMapper.selectOwnedAssets(whose);
        }
        else if (which.equals("on_sale"))
        {
            assets = assetMapper.selectOnSaleAssets(whose);
        }
        else if (which.equals("created"))
        {
            assets = assetMapper.selectCreatedAssets(whose);
        }
        else if (isMe && which.equals("auditing"))
        {
            assets = assetMapper.selectAuditingAssets(whose);
        }
        else if (isMe && which.equals("favorite"))
        {
            assets = assetMapper.selectFavoriteAssets(whose);
        }

        if (assets != null)
        {
            //太费时间
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

            List<Map<String, String>> maps = new ArrayList<>();
            for (ShowAsset obj : assets)
            {
                maps.add(obj.marketing());
            }
            return maps;
        }
        return null;
    }

    public String uploadAsset(String userAddress, Map<String, String> req, MultipartFile file) throws IOException
    {
        Asset asset = new Asset();
        asset.setCreator(userAddress);
        String hash = FileUtils.getFileHash(file);
        asset.setHash(hash);
        String route = IpfsUtils.upload(file.getBytes());
        asset.setIpfsCid(route);
        if (req.containsKey("name")) asset.setName(req.get("name"));
        if (req.containsKey("type")) asset.setType(req.get("type"));
        if (req.containsKey("desc")) asset.setDesc(req.get("desc"));
        if (req.containsKey("label")) asset.setLabel(req.get("label"));

        if (assetMapper.insertAsset(asset))
            return asset.getIpfsLink();
        return null;
    }

    public boolean mintAsset(String address, Long aid, int rate) throws IOException
    {
        if (!assetMapper.checkAidCreator(aid, address))
            return false;
        Asset asset = assetMapper.getAssetByAid(aid);
        asset.setRate(rate);
        asset.setOwner(asset.getCreator());

        String tokenJson = asset.toIpfsJson();
        String jsonCid = IpfsUtils.upload(tokenJson);
//        System.out.println(jsonCid);
        String json = vntUtils.mint(asset.getCreator(), jsonCid);
//        System.out.println(json);
        TxResult txResult = new Gson().fromJson(json, TxResult.class);
        long tokenId = Long.parseLong((String) txResult.getData());
        String txHash = txResult.getTxHash();
        System.out.println(txHash);
        System.out.println(tokenId);
        Event event = new Event("铸造", Event.NullAddress, address, tokenId, null, txHash);
        eventMapper.insertEventWithTxHash(event);
        asset.setState("已上链");
        asset.setJsonCid(jsonCid);
        asset.setTokenId(tokenId);

        return assetMapper.mintAsset(asset);
    }

    public boolean transfer(String from, String to, Long aid)
    {
        if (!checkOwner(from, aid)) return false;
//        System.out.println(from);
//        System.out.println(to);
//        System.out.println(aid);
        Asset asset = assetMapper.getAssetByAid(aid);
        long tokenId = asset.getTokenId();
//        System.out.println(tokenId);
        String json = vntUtils.transferToken(from, to, tokenId);
        TxResult txResult = new Gson().fromJson(json, TxResult.class);
        String newOwner = (String) txResult.getData();
        String txHash = txResult.getTxHash();

//        System.out.println(newOwner);
        if (newOwner.equals(to))
        {
//            System.out.println("11111");
            //todo 加入Event事件，transfer，同时，vnt中间件返回txHash
            Event event = new Event("转让Token", from, to, tokenId, null, txHash);
            eventMapper.insertEventWithTxHash(event);
            return assetMapper.updateOwner(aid, newOwner);
        }
        else
        {
            return false;
        }
    }

    public boolean updatePrice(String address, Long aid, int amount, boolean isUp)
    {
//        System.out.println("amount: " + amount);
        if (!checkOwner(address, aid)) return false;
        Asset asset = assetMapper.getAssetByAid(aid);
        long tokenId = asset.getTokenId();
        String json = vntUtils.setPrice(address, tokenId, amount);
//        System.out.println(json);
        TxResult result = new Gson().fromJson(json, TxResult.class);
        amount = Integer.parseInt((String) result.getData());
//        System.out.println("newPrice: " + amount);
        if (!isUp)
        {
            String txHash = result.getTxHash();
            Event event = new Event("更新价格", address, null, tokenId, amount, txHash);
            eventMapper.insertEventWithTxHash(event);
        }
        return assetMapper.updatePrice(aid, amount);
    }

    public boolean upAsset(String address, Long aid, int price)
    {
        if (checkOwner(address, aid))
        {
            //todo 添加价格，包括区块链和数据库
            //todo 添加一个上架Event
            updatePrice(address, aid, price, true);
            Asset asset = assetMapper.getAssetByAid(aid);
            eventMapper.insertEvent("上架", address, null, asset.getTokenId(), price);
            return assetMapper.updateState(aid, "在流通");
        }
        return false;
    }

    public boolean downAsset(String address, Long aid)
    {
        if (checkOwner(address, aid))
        {
            //todo 考虑，删除价格，包括区块链和数据库
            Asset asset = assetMapper.getAssetByAid(aid);
            eventMapper.insertEvent("下架", address, null, asset.getTokenId(), null);
            return assetMapper.updateState(aid, "未流通");
        }
        return false;
    }

    public void updateAsset(String address, Long aid, String name, String desc) throws CreatorNotCorrespondException, StateNotCorrespondException
    {
        Asset asset = assetMapper.getAssetByAid(aid);
        if (!address.equals(asset.getCreator()))
        {
            throw new CreatorNotCorrespondException();
        }
        if (asset.getTokenId() != null)
        {
            throw new StateNotCorrespondException();
        }
        if (name != null) assetMapper.updateName(aid, name);
        if (desc != null) assetMapper.updateDesc(aid, desc);
    }

    private boolean checkOwner(String address, Long aid)
    {
        Asset asset = assetMapper.getAssetByAid(aid);
        String owner = vntUtils.getOwner(asset.getTokenId());
        System.out.println(owner + " ======== " + address);
        return owner.equals(address);
    }
}