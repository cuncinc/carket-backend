package chunson.cc.carket.model;

public class Account
{
    private String address;
    private String username;
    private String pswHash;
    private String salt;
    private double balance;

    public Account(){}

    public Account(String address, String username, String pswHash, String salt)
    {
        this.address = address;
        this.username = username;
        this.pswHash = pswHash;
        this.salt = salt;
    }

    public Account(String address, String username, String pswHash, String salt, double balance)
    {
        this.address = address;
        this.username = username;
        this.pswHash = pswHash;
        this.salt = salt;
        this.balance = balance;
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
}
