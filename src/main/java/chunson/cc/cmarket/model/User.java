package chunson.cc.cmarket.model;

import chunson.cc.cmarket.utils.config.CosConfig;
import com.mysql.cj.util.StringUtils;

public class User
{
    private String userId;
    private String userName;
    private String password;
    private String avatarKey;
    private String avatarUrl;
    private double balance;
    private String phoneNo;
    private String qqNo;
    private String wechatId;

    private static final String AVATAR_URL_PREFIX;

    static
    {
        StringBuilder builder = new StringBuilder();
        builder.append("https://")
                .append(CosConfig.getBucketName())
                .append(".cos.")
                .append(CosConfig.getRegion())
                .append(".myqcloud.com/avatar/");

        AVATAR_URL_PREFIX = builder.toString();
    }

    public User() {}

    public User(String userId, String username, String avatarKey, double balance, String phoneNo, String qqNo, String wechatId)
    {
        this.userId = userId;
        this.userName = username;
        this.avatarKey = avatarKey;
        this.balance = balance;
        this.phoneNo = phoneNo;
        this.qqNo = qqNo;
        this.wechatId = wechatId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAvatarKey()
    {
        return avatarKey;
    }

    public void setAvatarKey(String avatarKey)
    {
        this.avatarKey = avatarKey;
    }

    public void setAvatarUrl(String avatarUrl)
    {
    }

    public String getAvatarUrl()
    {
        updateAvatarUrl();
        return avatarUrl;
    }

    public void updateAvatarUrl()
    {
        if (StringUtils.isNullOrEmpty(avatarKey))
        {
            avatarUrl = AVATAR_URL_PREFIX + "unknown.jpg";
        }
        else
        {
            avatarUrl = AVATAR_URL_PREFIX + avatarKey;
        }
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getPhoneNo()
    {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
    }

    public String getQqNo()
    {
        return qqNo;
    }

    public void setQqNo(String qqNo)
    {
        this.qqNo = qqNo;
    }

    public String getWechatId()
    {
        return wechatId;
    }

    public void setWechatId(String wechatId)
    {
        this.wechatId = wechatId;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", avatarKey='" + avatarKey + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", balance=" + balance +
                ", phoneNo='" + phoneNo + '\'' +
                ", qqNo='" + qqNo + '\'' +
                ", wechatId='" + wechatId + '\'' +
                '}';
    }
}
