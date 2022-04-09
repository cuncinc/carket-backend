package chunson.cc.carket.controller;

import chunson.cc.carket.model.Result;
import chunson.cc.carket.model.User;
import chunson.cc.carket.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/user/{address}")
    public Result<?> changePassword(@RequestBody Map<String, String> req, @CookieValue("token") String token, @PathVariable String address)
    {
        return new Result();
    }
}
