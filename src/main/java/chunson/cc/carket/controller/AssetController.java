package chunson.cc.carket.controller;

import chunson.cc.carket.model.Result;
import chunson.cc.carket.service.AccountService;
import chunson.cc.carket.service.AssetService;
import chunson.cc.carket.utils.TokenUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AssetService assetService;

    @Autowired
    private AccountService accountService;


    @PostMapping("/assets")
    public Result<?> createAssets(@CookieValue("token") String token, @RequestParam Map<String, String> req, @NotNull @RequestParam("file") MultipartFile file)
    {
        String userAddress = TokenUtils.getAddress(token);
        if (null == userAddress)
        {
            return new Result(HttpStatus.UNAUTHORIZED);
        }

        String link;
        try
        {
            link = assetService.uploadAsset(userAddress, req, file);
        }
        catch (IOException e)
        {
            return new Result(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (link != null)
        {
            Map<String, String> obj = new HashMap<>();
            obj.put("link", link);
            return new Result<>(obj, HttpStatus.CREATED);
        }
        return new Result(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/assets/{aid}/mint")
    public Result<?> mintAssets(@CookieValue("token") String token, @RequestParam Map<String, String> req, @PathVariable int aid)
    {
        String userAddress = TokenUtils.getAddress(token);
        if (null == userAddress)
        {
            return new Result(HttpStatus.UNAUTHORIZED);
        }

        try
        {
            int rate = Integer.parseInt(req.get("rate"));
            int price = Integer.parseInt(req.get("price"));
            if (assetService.mintAsset(userAddress, aid, price, rate))
            {
                return new Result(HttpStatus.CREATED);
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
    public Result<?> transfer(@CookieValue("token") String token, @PathVariable int aid, @RequestParam Map<String, String> req)
    {
        String userAddress = TokenUtils.getAddress(token);
        if (null == userAddress)
        {
            return new Result(HttpStatus.UNAUTHORIZED);
        }

        String to = req.get("to");

        if (assetService.transfer(userAddress, to, aid))
        {
            return new Result();
        }

        return new Result(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/myAssets")
    public Result<?> getAssets(@CookieValue("token") String token)
    {
        String me = TokenUtils.getAddress(token);
        List<Map<String, String>> maps = assetService.myAssets(me);
        return new Result<>(maps);
    }

    @GetMapping("/assets")
    public Result<?> getAssets(@RequestParam Map<String, String> req)
    {
        if ( !req.containsKey("page") || !req.containsKey("num"))
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

    @PostMapping("/assets/{aid}")
    public Result<?> upAssets(@CookieValue("token") String token, @PathVariable int aid)
    {
        String userAddress = TokenUtils.getAddress(token);
        if (null == userAddress)
        {
            return new Result(HttpStatus.UNAUTHORIZED);
        }

        if (assetService.upAsset(userAddress, aid))
            return new Result<>(HttpStatus.CREATED);

        return new Result(HttpStatus.FORBIDDEN);
    }

//    @PostMapping("/buy/{aid}")
//    public Result<?> buy(@CookieValue("token") String token, @PathVariable int aid)
//    {
//
//    }

    @DeleteMapping("/assets/{aid}")
    public Result<?> downAssets(@CookieValue("token") String token, @PathVariable int aid)
    {
        String userAddress = TokenUtils.getAddress(token);
        if (null == userAddress)
        {
            return new Result(HttpStatus.UNAUTHORIZED);
        }

        if (assetService.downAsset(userAddress, aid))
            return new Result<>();

        return new Result(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/assets/{aid}")
    public Result<?> updateAssets(@CookieValue("token") String token, @RequestParam Map<String, String> req, @PathVariable int aid)
    {
        String userAddress = TokenUtils.getAddress(token);
        if (null != userAddress)
        {
            int price = Integer.parseInt(req.get("price"));
            if (assetService.updatePrice(userAddress, aid, price))
                return new Result();
        }
        return new Result(HttpStatus.UNAUTHORIZED);
    }

//    @GetMapping("/user/{userAddress}")
//    public Result<?> getAsset(@PathVariable String userAddress)
//    {
//        Asset user = assetService.getAssetByAddress(userAddress);
//        if (user != null)
//            return new Result<>(user);
//
//        return new Result<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping("/user")
//    public Result<?> getMe(@CookieValue("token") String token)
//    {
//        String userAddress = TokenUtils.getAddress(token);
//        if (userAddress != null)
//        {
//            Asset me = assetService.getAssetByAddress(userAddress);
//            if (me != null)
//                return new Result<>(me);
//        }
//
//        return new Result<>(HttpStatus.UNAUTHORIZED);
//    }
//
//    @PutMapping("/user")
//    public Result<?> updateAsset(@RequestBody Map<String, String> req, @CookieValue("token") String token)
//    {
//        String userAddress = TokenUtils.getAddress(token);
//        if (userAddress == null)
//            return new Result(HttpStatus.UNAUTHORIZED);
//
//        if (req.containsKey("username"))
//        {
//            String username = req.get("username");
//            if (accountService.existsAccount(username))
//            {
//                return new Result<>(HttpStatus.FORBIDDEN);
//            }
//        }
//
//        if (assetService.updateAsset(userAddress, req))
//            return new Result();
//        return new Result(HttpStatus.NOT_FOUND);
//    }
//
//    @PutMapping("/user/{type}")
//    public Result<?> updateAvatar(@NotNull @RequestParam("file") MultipartFile file, @CookieValue("token") String token, @NotNull @PathVariable String type)
//    {
//        String userAddress = TokenUtils.getAddress(token);
//        if (null == userAddress)
//        {
//            return new Result(HttpStatus.UNAUTHORIZED);
//        }
//        String key = type + "Link";
//        AssetService.ImgType imgType;
//        if (type.equals("avatar"))
//        {
//            imgType = AssetService.ImgType.Avatar;
//        }
//        else if (type.equals("cover"))
//        {
//            imgType = AssetService.ImgType.Cover;
//        }
//        else//路径既不是avatar也不是cover，错误路径
//        {
//            return new Result<>(HttpStatus.BAD_REQUEST);
//        }
//
//        String link = assetService.updateImgResource(userAddress, file, imgType);
//        if (link != null)
//        {
//            Map<String, String> map = new HashMap<>();
//            map.put(key, link);
//            return new Result<>(map);
//        }
//        else//不能更新，userAddress错误，未授权
//        {
//            return new Result<>(HttpStatus.UNAUTHORIZED);
//        }
//    }
}
