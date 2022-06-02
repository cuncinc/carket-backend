package chunson.cc.carket.controller;

import chunson.cc.carket.Exception.CreatorNotCorrespondException;
import chunson.cc.carket.Exception.StateNotCorrespondException;
import chunson.cc.carket.model.Asset;
import chunson.cc.carket.model.Result;
import chunson.cc.carket.service.AssetService;
import chunson.cc.carket.utils.TokenUtils;
import com.mysql.cj.util.StringUtils;
import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class AssetController
{
    private final AssetService assetService;

    public AssetController(AssetService assetService)
    {
        this.assetService = assetService;
    }

    @PostMapping("/assets")
    public Result<?> createAssets(@CookieValue("token") String token, @RequestParam Map<String, String> req, @NotNull @RequestParam("file") MultipartFile file)
    {
        String userAddress = TokenUtils.getAddress(token);
        if (null == userAddress)
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }

        String link;
        try
        {
            link = assetService.uploadAsset(userAddress, req, file);
        }
        catch (IOException e)
        {
            return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (link != null)
        {
            Map<String, String> obj = new HashMap<>();
            obj.put("link", link);
            return new Result<>(obj, HttpStatus.CREATED);
        }
        return new Result<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/assets/{aid}/blockchain")
    public Result<?> mintAssets(@CookieValue("token") String token, @RequestBody Map<String, String> req, @PathVariable long aid)
    {
        String userAddress = TokenUtils.getAddress(token);
        if (null == userAddress)
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }

        try
        {
            int rate = Integer.parseInt(req.get("rate"));
            if (assetService.mintAsset(userAddress, aid, rate))
            {
                return new Result<>(HttpStatus.CREATED);
            }
            else
            {
                Map<String, String> map = new HashMap<>();
                map.put("error", "can't mint assets");
                return new Result<>(map, HttpStatus.FORBIDDEN);
            }
        }
        catch (Exception e)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "can't upload to IPFS");
            map.put("e", e);
            return new Result<>(map, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/assets/{aid}/owner")
    public Result<?> transfer(@CookieValue("token") String token, @PathVariable long aid, @RequestBody Map<String, String> req)
    {
        String userAddress = TokenUtils.getAddress(token);
        if (null == userAddress)
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }

        String to = req.get("to");

        if (userAddress.equals(to))
        {
            return new Result<>(HttpStatus.BAD_REQUEST);
        }

        if (assetService.transfer(userAddress, to, aid))
        {
            return new Result<>();
        }

        return new Result<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/assets/{whose}/{which}")
    public Result<?> getAssets(@CookieValue(value = "token", required = false) String token, @NotNull @PathVariable String whose, @NotNull @PathVariable String which)
    {
        if (!(which.equals("created") || which.equals("on_sale") || which.equals("owned") || which.equals("auditing") || which.equals("favorite")))
        {
            return new Result<>(HttpStatus.BAD_REQUEST);
        }

        //自己看自己的视角
        if (!StringUtils.isNullOrEmpty(token))
        {
            String meAddress = TokenUtils.getAddress(token);
            if (whose.equals(meAddress) || whose.equals("my"))
            {
                return new Result<>(assetService.getAssetsOfUser(meAddress, which, true));
            }
        }

        //访客视角没有“审核中”和“收藏”2个标签
        if (which.equals("auditing") || which.equals("favorite"))
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }
        else
        {
            return new Result<>(assetService.getAssetsOfUser(whose, which, false));
        }
    }

    @GetMapping("/assets")
    public Result<?> getAssets(@RequestParam Map<String, String> req)
    {
        if (!req.containsKey("page") || !req.containsKey("num"))
            return new Result<>(HttpStatus.BAD_REQUEST);

        int page, num;

        try
        {
            page = Integer.parseInt(req.get("page"));
            num = Integer.parseInt(req.get("num"));
        }
        catch (NumberFormatException e)
        {
            return new Result<>(HttpStatus.BAD_REQUEST);
        }

        List<Map<String, String>> maps = assetService.getAssets(page, num);
        return new Result<>(maps);
    }

    @PostMapping("/assets/{aid}/market")
    public Result<?> upAsset(@CookieValue("token") String token, @PathVariable long aid, @RequestBody Map<String, String> req)
    {
        String userAddress = TokenUtils.getAddress(token);
        if (null == userAddress)
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }
        int price = Integer.parseInt(req.get("price"));
        if (assetService.upAsset(userAddress, aid, price))
            return new Result<>(HttpStatus.CREATED);

        return new Result<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/assets/{id}")
    public Result<?> getOneAsset(@CookieValue(value = "token", required = false) String token, @PathVariable long id, @RequestParam("type") String type)
    {
        String me = null;
        if (token != null)
            me = TokenUtils.getAddress(token);
        Asset asset;

        if (type.equals("aid"))
        {
            asset = assetService.getOneAssetByAid(id);
        }
        else if (type.equals("token_id"))
        {
            asset = assetService.getOneAssetByTokenId(id);
        }
        else
        {
            return new Result<>(HttpStatus.BAD_REQUEST);
        }

        if (asset != null)
        {
            if (asset.getTokenId() == null)
            {
                if (me == null || !me.equals(asset.getCreator()))
                {
                    return new Result<>(HttpStatus.UNAUTHORIZED);
                }
            }

            return new Result<>(asset);
        }
        else
        {
            return new Result<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/assets/{aid}/market")
    public Result<?> downAssets(@CookieValue("token") String token, @PathVariable long aid)
    {
        String userAddress = TokenUtils.getAddress(token);
        if (null == userAddress)
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }

        if (assetService.downAsset(userAddress, aid))
            return new Result<>();

        return new Result<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/assets/{aid}")//修改未上链的艺术品的信息
    public Result<?> updateAssets(@CookieValue("token") String token, @RequestBody Map<String, String> body, @PathVariable long aid)
    {
        String name = body.get("name");
        String desc = body.get("desc");
        Map<String, String> map = new HashMap<>();
        if (name == null && desc == null)
        {
            return new Result<>(HttpStatus.BAD_REQUEST);
        }

        String address = TokenUtils.getAddress(token);
        if (null != address)
        {
            try
            {
                assetService.updateAsset(address, aid, name, desc);
                return new Result<>();
            }
            catch (CreatorNotCorrespondException e)
            {
                e.printStackTrace();
                map.put("message", "creator of token is not you");
                return new Result<>(map, HttpStatus.UNAUTHORIZED);
            }
            catch (StateNotCorrespondException e)
            {
                e.printStackTrace();
                map.put("message", "token is on chain, can not be changed");
                return new Result<>(map, HttpStatus.FORBIDDEN);
            }
        }
        else
        {
            map.put("message", "token error!");
            return new Result<>(map, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/assets/{aid}/price")//修改未上链后的艺术品的价格
    public Result<?> setPrice(@CookieValue("token") String token, @PathVariable long aid, @RequestBody Map<String, Object> req)
    {
        Integer price = (Integer) req.get("price");
        if (price == null)
        {
            return new Result<>(HttpStatus.BAD_REQUEST);
        }

        String userAddress = TokenUtils.getAddress(token);
        if (null != userAddress)
        {
            if (assetService.updatePrice(userAddress, aid, price, false))
                return new Result<>();
        }
        return new Result<>(HttpStatus.UNAUTHORIZED);
    }
}
