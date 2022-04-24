package chunson.cc.carket.service;

import chunson.cc.carket.mapper.AccountMapper;
import chunson.cc.carket.model.Account;
import chunson.cc.carket.utils.FileUtils;
import chunson.cc.carket.utils.PswUtils;
import chunson.cc.carket.utils.TokenUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


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

    public boolean logon(String username, String password) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException
    {
        if (existsAccount(username))
            return false;
        String hash = PswUtils.hashPassword(password);
        String walletPsw = PswUtils.randomPassword();
        String name = WalletUtils.generateNewWalletFile(walletPsw, new File(FileUtils.getWalletPath()));

        int origin = name.lastIndexOf('-') + 1;
        String address = "0x" + name.substring(origin, origin + 40);
        Account account = new Account(address, username, hash, password, walletPsw, name);
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
        if (TokenUtils.isTokenOK(token))
        {
            return TokenUtils.refreshToken(token);
        }

        return null;
    }

    public Account getAccountByUsername(String username)
    {
        return mapper.getAccountByName(username);
    }

    //只能查询自己的余额，并且需要登录
    public double getBalance(String token)
    {
        @NotNull String address = TokenUtils.getAddress(token);
        return mapper.getBalance(address);
    }
}
