package chunson.cc.carket.controller;

import chunson.cc.carket.mapper.AccountMapper;
import chunson.cc.carket.model.Account;
import chunson.cc.carket.model.Result;
import chunson.cc.carket.utils.PswUtils;
import chunson.cc.carket.utils.TokenUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class AccountController
{
    private AccountMapper mapper;
    private TokenUtils tokenUtils;

    @Autowired
    public AccountController(AccountMapper mapper, TokenUtils tokenUtils)
    {
        this.mapper = mapper;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping("/account")
//    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="用户名已被占用")
    public Result<?> isNullOrEmpty(@RequestParam Map<String, String> req)
    {
        @NotNull String username = req.get("username");
        if (!mapper.existsAccount(username))
        {
            return new Result<>();
        }
        else
        {
            return new Result<Error>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/account")
//    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="用户名已被占用")
    public Result<?> logon(@RequestBody Map<String, String> req)
    {
        @NotNull String username = req.get("username");
        @NotNull String password = req.get("password");

        String hash = PswUtils.hashPassword(password);
        System.out.println(hash);
        Account account = new Account(username, username, hash, password);
        if (mapper.insertAccount(account))
        {
            return new Result<>(account, HttpStatus.CREATED);
        }
        else
        {
            return new Result<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/account")
    public Result<?> deleteAccount(@RequestBody Map<String, String> req, @CookieValue("token") String token)
    {
        if (!TokenUtils.isTokenExpired(token))
        {
            String address = TokenUtils.getAddressFromToken(token);
            Account account = mapper.getAccountByAddress(address);

            @NotNull String password = req.get("password");
            if (PswUtils.checkPassword(password, account.getPswHash())
                && mapper.deleteAccount(address))
            {
                return new Result();
            }
        }

        return new Result<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/account")
    public Result<?> changePassword(@RequestBody Map<String, String> req, @CookieValue("token") String token)
    {
        if (!TokenUtils.isTokenExpired(token))
        {
            String address = TokenUtils.getAddressFromToken(token);
            Account account = mapper.getAccountByAddress(address);

            @NotNull String oldPassword = req.get("old_psw");
            @NotNull String newPassword = req.get("new_psw");
            String newPswHash = PswUtils.hashPassword(newPassword);

            if (PswUtils.checkPassword(oldPassword, account.getPswHash())
               && mapper.updatePswHash(address, newPswHash))
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

        Account account = mapper.getAccountByName(username);
        if (PswUtils.checkPassword(password, account.getPswHash()))
        {
            String token = TokenUtils.generateToken(username);
            return new Result<>(token, HttpStatus.CREATED);
        }
        else
        {
            return new Result(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/session")
    public Result<?> logout(@CookieValue("token") String token)
    {
        String address = TokenUtils.getAddressFromToken(token);
        System.out.println("logout: " + address);

        //todo 使用redis屏蔽掉token

        return new Result();
    }
}
