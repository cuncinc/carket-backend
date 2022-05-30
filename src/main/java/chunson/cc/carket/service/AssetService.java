package chunson.cc.carket.service;

import chunson.cc.carket.mapper.AssetMapper;
import chunson.cc.carket.mapper.EventMapper;
import chunson.cc.carket.model.Asset;
import chunson.cc.carket.model.ShowAsset;
import chunson.cc.carket.utils.FileUtils;
import chunson.cc.carket.utils.VNTUtils;
import chunson.cc.carket.utils.IpfsUtils;
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
    private final VNTUtils vntUtils;

    public AssetService(AssetMapper assetMapper, EventMapper eventMapper, VNTUtils vntUtils)
    {
        this.assetMapper = assetMapper;
        this.eventMapper = eventMapper;
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
            //todo
        }

        if (assets != null)
        {
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

        String json = asset.toIpfsJson();
        String jsonCid = IpfsUtils.upload(json);
//        System.out.println(jsonCid);
        long tokenId = vntUtils.mint(asset.getCreator(), jsonCid);

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
        String newOwner = vntUtils.transferToken(from, to, tokenId);
//        System.out.println(newOwner);
        if (newOwner.equals(to))
        {
//            System.out.println("11111");
            //todo 加入Event事件，transfer，同时，vnt中间件返回txHash
            return assetMapper.updateOwner(aid, newOwner);
        }
        else
        {
            return false;
        }
    }

    public boolean updatePrice(String address, Long aid, int price)
    {
        if (!checkOwner(address, aid)) return false;
        Asset asset = assetMapper.getAssetByAid(aid);
        long tokenId = asset.getTokenId();
//        vntUtils.setPrice(address, tokenId, price);  //todo
        return assetMapper.updatePrice(aid, price);
    }

    public boolean upAsset(String address, Long aid, int price)
    {
        if (checkOwner(address, aid))
        {
            //todo 添加价格，包括区块链和数据库
            //todo 添加一个上架Event
            updatePrice(address, aid, price);
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

    public void updateAsset(Long aid)
    {

    }

    private boolean checkOwner(String address, Long aid)
    {
        Asset asset = assetMapper.getAssetByAid(aid);
        String owner = vntUtils.getOwner(asset.getTokenId());
        System.out.println(owner + " ======== " + address);
        return owner.equals(address);
    }
}