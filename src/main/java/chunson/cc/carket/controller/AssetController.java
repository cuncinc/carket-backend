package chunson.cc.carket.controller;

import chunson.cc.carket.model.Result;
import chunson.cc.carket.model.Asset;
import chunson.cc.carket.service.AccountService;
import chunson.cc.carket.service.AssetService;
import chunson.cc.carket.utils.FileUtils;
import chunson.cc.carket.utils.TokenUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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
        String link = assetService.insertAsset(userAddress, req, file);
        if (link != null)
        {
            Map<String, String> obj = new HashMap<>();
            obj.put("link", link);
            return new Result<>(obj, HttpStatus.CREATED);
        }
        return new Result(HttpStatus.FORBIDDEN);
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
