package chunson.cc.carket.controller;

import chunson.cc.carket.model.Result;
import chunson.cc.carket.service.AccountService;
import chunson.cc.carket.utils.TokenUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class AccountController
{
    @Autowired
    private AccountService service;

    @GetMapping("/account")
//    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="用户名已被占用")
    public Result<?> existsAccount(@RequestParam Map<String, String> req)
    {
        @NotNull String username = req.get("username");
        if (!service.existsAccount(username))
            return new Result();

        return new Result<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/account")
//    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="用户名已被占用")
    public Result<?> logon(@RequestBody Map<String, String> req)
    {
        @NotNull String username = req.get("username");
        @NotNull String password = req.get("password");

        if (service.logon(username, password))
            return new Result(HttpStatus.CREATED);

        return new Result<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/account")
    public Result<?> deleteAccount(@RequestBody Map<String, String> req, @CookieValue("token") String token)
    {
        if (token.equals(""))
            return new Result<>(HttpStatus.BAD_REQUEST);

        if (!TokenUtils.isTokenExpired(token))
        {
            String address = TokenUtils.getAddress(token);
            @NotNull String password = req.get("password");
            if (service.deleteAccount(address, password))
            {
                return new Result();
            }
        }

        return new Result<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/account")
    public Result<?> updatePassword(@RequestBody Map<String, String> req, @CookieValue("token") String token)
    {
        if (token.equals(""))
            return new Result<>(HttpStatus.BAD_REQUEST);

        if (!TokenUtils.isTokenExpired(token))
        {
            String address = TokenUtils.getAddress(token);
            @NotNull String oldPassword = req.get("old_psw");
            @NotNull String newPassword = req.get("new_psw");
            if (service.updatePassword(address, oldPassword, newPassword))
            {
               return new Result();
            }
        }

        return new Result<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/session")
    public Result<?> login(@RequestBody Map<String, String> req)
    {
        @NotNull String username = req.get("username");
        @NotNull String password = req.get("password");

        String token = service.login(username, password);

        if (token != null)
        {
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            return new Result<>(map, HttpStatus.CREATED);
        }

        return new Result<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/session")
    public Result<?> logout(@CookieValue("token") String token)
    {
        service.logout(token);
        return new Result();
    }

    @PutMapping("/session")
    public Result<?> updateToken(@NotNull @CookieValue("token") String token)
    {
        if (token.equals(""))
            return new Result<>(HttpStatus.BAD_REQUEST);
        String newToken = service.updateToken(token);
        if (newToken != null)
        {
            return new Result<>(newToken);
        }
        return new Result(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/session")
    public Result<?> checkToken(@NotNull @CookieValue("token") String token)
    {
        if (!TokenUtils.isTokenExpired(token))
            return new Result<>();

        return new Result(HttpStatus.UNAUTHORIZED);
    }
}