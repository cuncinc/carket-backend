package chunson.cc.carket.model;

public class Account
{
    private String address;
    private String username;
    private String pswHash;
    private String salt;
    private double balance;
    private String walletPsw;
    private String walletName;

    public Account(){}

    public Account(String address, String username, String pswHash, String salt, String walletPsw, String walletName)
    {
        this.address = address;
        this.username = username;
        this.pswHash = pswHash;
        this.salt = salt;
        this.walletPsw = walletPsw;
        this.walletName = walletName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPswHash()
    {
        return pswHash;
    }

    public void setPswHash(String pswHash)
    {
        this.pswHash = pswHash;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getWalletPsw()
    {
        return walletPsw;
    }

    public void setWalletPsw(String walletPsw)
    {
        this.walletPsw = walletPsw;
    }

    public String getWalletName()
    {
        return walletName;
    }

    public void setWalletName(String walletName)
    {
        this.walletName = walletName;
    }
}
