package chunson.cc.cmarket.model;


public class User
{
    private String userId;
    private String userName;
//    private String password;
    private double balance;
    private String phoneNum;
    private String qqNo;
    private String wechatId;

    public User(String userId, String userName, double balance, String phoneNum, String qqNo, String wechatId)
    {
        this.userId = userId;
        this.userName = userName;
        this.balance = balance;
        this.phoneNum = phoneNum;
        this.qqNo = qqNo;
        this.wechatId = wechatId;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public double getBalance()
    {
        return balance;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public String getQqNo()
    {
        return qqNo;
    }

    public String getWechatId()
    {
        return wechatId;
    }
}
