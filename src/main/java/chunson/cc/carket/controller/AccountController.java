package chunson.cc.carket.controller;

import chunson.cc.carket.model.Account;
import chunson.cc.carket.model.Result;
import chunson.cc.carket.service.AccountService;
import chunson.cc.carket.utils.TokenUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
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

        return new Result<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/account")
//    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="用户名已被占用")
    public Result<?> logon(@RequestBody Map<String, String> req)
    {
        @NotNull String username = req.get("username");
        @NotNull String password = req.get("password");

        if (username == null || password == null)
        {
            return new Result<>(HttpStatus.BAD_REQUEST);
        }

        try
        {
            if (service.logon(username, password))
            {
                Account account = service.getAccountByUsername(username);
                Map<String, String> map = new HashMap();
                map.put("address", account.getAddress());
                return new Result<>(map, HttpStatus.CREATED);
            }
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | CipherException | IOException e)
        {
            e.printStackTrace();
            return new Result(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new Result<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/account")
    public Result<?> deleteAccount(@RequestBody Map<String, String> req, @CookieValue("token") String token)
    {
        if (token.equals(""))
            return new Result<>(HttpStatus.BAD_REQUEST);

        if (TokenUtils.isTokenOK(token))
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

        if (TokenUtils.isTokenOK(token))
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
            Map<String, String> map = new HashMap<>();
            map.put("newToken", newToken);
            return new Result<>(map);
        }
        return new Result(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/session")
    public Result<?> checkToken(@NotNull @CookieValue("token") String token)
    {
        if (TokenUtils.isTokenOK(token))
            return new Result<>();

        return new Result(HttpStatus.UNAUTHORIZED);
    }
}
