package chunson.cc.carket.controller;

import chunson.cc.carket.model.Result;
import chunson.cc.carket.model.User;
import chunson.cc.carket.service.UserService;
import chunson.cc.carket.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/user/{address}")
    public Result<?> isNullOrEmpty(@RequestParam Map<String, String> req, @PathVariable String address)
    {
        User user = userService.getUserByAddress(address);
        return new Result<>(user);
    }

    @PutMapping("/user")
    public Result<?> updateUser(@RequestBody Map<String, String> req, @CookieValue("token") String token)
    {
        String address = TokenUtils.getAddress(token);

        if (userService.updateUser(address, req))
            return new Result();
        return new Result(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/user/avatar")
    public Result<?> updateAvatar(@RequestParam("avatar") MultipartFile file, @CookieValue("token") String token) throws IOException
    {
        String address = TokenUtils.getAddress(token);
        String route = userService.updateAvatar(address, file);
        if (route != null)
            return new Result<>(route);

        return new Result(HttpStatus.UNAUTHORIZED);
    }
}
