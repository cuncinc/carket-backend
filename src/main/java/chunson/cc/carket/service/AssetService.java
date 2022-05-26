package chunson.cc.carket.service;

import chunson.cc.carket.mapper.AssetMapper;
import chunson.cc.carket.model.Asset;
import chunson.cc.carket.model.ShowAsset;
import chunson.cc.carket.utils.FileUtils;
import chunson.cc.carket.utils.VNTUtils;
import chunson.cc.carket.utils.IpfsUtils;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AssetService
{
    @Autowired
    private AssetMapper mapper;

    @Autowired
    private VNTUtils vntUtils;

    public Asset getAssetByAid(Long aid)
    {
        Asset asset = mapper.getAssetByAid(aid);
        return asset;
    }

    public List<Map<String, String>> getAssets(@NotNull int page, int num)
    {
        List<ShowAsset> assets = mapper.selectAssets((page - 1) * num, num);
        List<Map<String, String>> maps = new ArrayList<>();
        for (ShowAsset obj : assets)
        {
            maps.add(obj.marketing());
        }
        return maps;
    }

    public Asset getOneAssetByAid(@NotNull Long aid, @Nullable String me)
    {
        //todo 确定权限，添加创作者、所有者信息
        Asset asset = mapper.selectOneAssetByAid(aid);
        if (null == asset) return null;
        return asset;
    }

    public Asset getOneAssetByTokenId(@NotNull Long tokenId)
    {
        //todo 添加创作者、所有者信息
        Asset asset = mapper.selectOneAssetByTokenId(tokenId);
        return asset;
    }

    public List<Map<String, String>> getAssetsOfUser(@NotNull String whose, @NotNull String which, @NotNull boolean isMe)
    {
        List<ShowAsset> assets = null;
        if (which.equals("owned"))
        {
            assets = mapper.selectOwnedAssets(whose);
        }
        else if (which.equals("on_sale"))
        {
            assets = mapper.selectOnSaleAssets(whose);
        }
        else if (which.equals("created"))
        {
            assets = mapper.selectCreatedAssets(whose);
        }
        else if (isMe && which.equals("auditing"))
        {
            assets = mapper.selectAuditingAssets(whose);
        }
        else if (isMe && which.equals("favorite"))
        {
            //todo
        }

        List<Map<String, String>> maps = new ArrayList<>();
        for (ShowAsset obj : assets)
        {
            maps.add(obj.marketing());
        }
        return maps;
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

        if (mapper.insertAsset(asset))
            return asset.getIpfsLink();
        return null;
    }

    public boolean mintAsset(String address, Long aid, int price, int rate) throws IOException
    {
        if (!mapper.checkAidCreator(aid, address))
            return false;
        Asset asset = mapper.getAssetByAid(aid);
        asset.setRate(rate);
        asset.setOwner(asset.getCreator());

        String json = asset.toIpfsJson();
        String jsonCid = IpfsUtils.upload(json);
        System.out.println(jsonCid);
        long tokenId = vntUtils.mint(asset.getCreator(), jsonCid);

        asset.setState("已上链");
        asset.setJsonCid(jsonCid);
        asset.setTokenId(tokenId);

        return mapper.mintAsset(asset) && updatePrice(address, aid, price);
    }

    public boolean transfer(String from, String to, Long aid)
    {
        if (!checkOwner(from, aid)) return false;
        Asset asset = mapper.getAssetByAid(aid);
        long tokenId = asset.getTokenId();
        String owner = vntUtils.transferToken(from, to, tokenId);
        if (owner.equals(to))
        {
            return mapper.updateOwner(aid, owner);
        }
        return false;
    }

    public boolean updatePrice(String address, Long aid, int price)
    {
        if (!checkOwner(address, aid)) return false;
        Asset asset = mapper.getAssetByAid(aid);
        long tokenId = asset.getTokenId();
        vntUtils.setPrice(address, tokenId, price);
        return mapper.updatePrice(aid, price);
    }

    public boolean upAsset(String address, Long aid)
    {
        if (checkOwner(address, aid))
            return mapper.updateState(aid, "在流通");
        return false;
    }

    public boolean downAsset(String address, Long aid)
    {
        if (checkOwner(address, aid))
            return mapper.updateState(aid, "已下架");
        return false;
    }

    public void updateAsset(Long aid)
    {

    }

    private boolean checkOwner(String address, Long aid)
    {
        Asset asset = mapper.getAssetByAid(aid);
        String owner = vntUtils.getOwner(asset.getTokenId());
        System.out.println(owner + " ======== " + address);
        return owner.equals(address);
    }
}