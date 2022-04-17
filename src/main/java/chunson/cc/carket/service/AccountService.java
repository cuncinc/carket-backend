package chunson.cc.carket.service;

import chunson.cc.carket.mapper.AccountMapper;
import chunson.cc.carket.model.Account;
import chunson.cc.carket.utils.PswUtils;
import chunson.cc.carket.utils.TokenUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountService
{
    @Autowired
    private AccountMapper mapper;

    public boolean existsAccount(@NotNull String username)
    {
        return mapper.existsAccount(username);
    }

    public boolean checkAccount(@NotNull String address, String password)
    {
        Account account = mapper.getAccountByAddress(address);
        if (account != null && account.getPswHash() != null)
            return PswUtils.checkPassword(password, account.getPswHash());

        return false;
    }

    public boolean logon(String username, String password)
    {
        String hash = PswUtils.hashPassword(password);
        Account account = new Account(username, username, hash, password);
        return mapper.insertAccount(account);
    }

    public boolean deleteAccount(String address, String password)
    {
        return (checkAccount(address, password) && mapper.deleteAccount(address));
    }

    public boolean updatePassword(String address, String oldPassword, String newPassword)
    {
        String newPswHash = PswUtils.hashPassword(newPassword);
        return (checkAccount(address, oldPassword) && mapper.updatePswHash(address, newPswHash));
    }

    public String login(@NotNull String username, @NotNull String password)
    {
        if (mapper.existsAccount(username))
        {
            Account account = mapper.getAccountByName(username);
            String address = account.getAddress();
            if (checkAccount(address, password))
            {
                return TokenUtils.generateToken(address);
            }
        }

        return null;
    }

    public void logout(String token)
    {
        String address = TokenUtils.getAddress(token);

        //todo 使用redis屏蔽掉token
    }

    public String updateToken(@NotNull String token)
    {
        if (!TokenUtils.isTokenExpired(token))
        {
            return TokenUtils.refreshToken(token);
        }

        return null;
    }

    //只能查询自己的余额，并且需要登录
    public double getBalance(String token)
    {
        @NotNull String address = TokenUtils.getAddress(token);
        return mapper.getBalance(address);
    }
}