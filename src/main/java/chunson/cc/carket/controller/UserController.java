package chunson.cc.carket.controller;

import chunson.cc.carket.model.Result;
import chunson.cc.carket.model.User;
import chunson.cc.carket.service.AccountService;
import chunson.cc.carket.service.UserService;
import chunson.cc.carket.utils.TokenUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/users/{address}")
    public Result<?> getUser(@PathVariable String address)
    {
        User user = userService.getUserByAddress(address);
        if (user != null)
            return new Result<>(user);

        return new Result<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/me")
    public Result<?> getMe(@CookieValue("token") String token)
    {
        String address = TokenUtils.getAddress(token);
        if (address != null)
        {
            User me = userService.getUserByAddress(address);
            if (me != null)
                return new Result<>(me);
        }

        return new Result<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/users/me")
    public Result<?> updateUser(@RequestBody Map<String, String> req, @CookieValue("token") String token)
    {
        String address = TokenUtils.getAddress(token);
        if (address == null)
            return new Result(HttpStatus.UNAUTHORIZED);

        if (req.containsKey("username"))
        {
            String username = req.get("username");
            if (accountService.existsAccount(username))
            {
                return new Result<>(HttpStatus.FORBIDDEN);
            }
        }

        if (userService.updateUser(address, req))
            return new Result();
        return new Result(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/users/me/{type}")
    public Result<?> updateAvatar(@NotNull @RequestParam("file") MultipartFile file, @CookieValue("token") String token, @NotNull @PathVariable String type)
    {
        String address = TokenUtils.getAddress(token);
        if (null == address)
        {
            return new Result(HttpStatus.UNAUTHORIZED);
        }
        String key = type + "Link";
        UserService.ImgType imgType;
        if (type.equals("avatar"))
        {
            imgType = UserService.ImgType.Avatar;
        }
        else if (type.equals("cover"))
        {
            imgType = UserService.ImgType.Cover;
        }
        else//路径既不是avatar也不是cover，错误路径
        {
            return new Result<>(HttpStatus.BAD_REQUEST);
        }

        String link = userService.updateImgResource(address, file, imgType);
        if (link != null)
        {
            Map<String, String> map = new HashMap<>();
            map.put(key, link);
            return new Result<>(map);
        }
        else//不能更新，address错误，未授权
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
