package chunson.cc.carket.model;

public class Account
{
    private String address;
    private String username;
    private String pswHash;
    private String salt;
    private double balance;
    private String privateKey;

    public Account(){}

    public Account(String address, String username, String pswHash, String salt, String privateKey)
    {
        this.address = address;
        this.username = username;
        this.pswHash = pswHash;
        this.salt = salt;
        this.privateKey = privateKey;
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

    public String getPrivateKey()
    {
        return privateKey;
    }

    public void setPrivateKey(String privateKey)
    {
        this.privateKey = privateKey;
    }
}
