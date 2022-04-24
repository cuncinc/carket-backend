package chunson.cc.carket.service;

import chunson.cc.carket.mapper.AssetMapper;
import chunson.cc.carket.model.Asset;
import chunson.cc.carket.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class AssetService
{    
    @Autowired
    private AssetMapper mapper;

    public Asset getAssetById(String aid)
    {
        Asset asset = mapper.getAssetById(aid);
        return asset;
    }

//    public boolean updateAsset(String aid, Map<String ,String> req)
//    {
//        Asset asset = getAssetById(aid);
//        if(req.containsKey("name")) asset.setName(req.get("name"));
//        return mapper.updateAsset(asset);
//    }

    public String insertAsset(String userAddress, Map<String, String> req, MultipartFile file)
    {
        Asset asset = new Asset();
        asset.setCreator(userAddress);
        String hash = FileUtils.getFileHash(file);
        asset.setHash(hash);
        String route = FileUtils.storeAssets(file);
        asset.setRoute(route);
        if (req.containsKey("name")) asset.setName(req.get("name"));
        if (req.containsKey("type")) asset.setType(req.get("type"));
        if (req.containsKey("desc")) asset.setDesc(req.get("desc"));
        if (req.containsKey("label")) asset.setLabel(req.get("label"));

        if (mapper.insertAsset(asset))
            return asset.getLink();
        return null;
    }
}