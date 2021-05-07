package chunson.cc.cmarket.model;

import chunson.cc.cmarket.utils.config.TencentCosConfigUtils;
import com.mysql.cj.util.StringUtils;

public class User
{
    private String userId;
    private String userName;
    private String password;
    private String avatarKey;
    private String avatarUrl;
    private double balance;
    private String phoneNum;
    private String qqNo;
    private String wechatId;

    private static final String avatarUrlPrefix;

    static
    {
        StringBuilder builder = new StringBuilder();
        builder.append("https://")
                .append(TencentCosConfigUtils.getBucketName())
                .append(".cos.")
                .append(TencentCosConfigUtils.getRegion())
                .append(".myqcloud.com/avatar/");

        avatarUrlPrefix = builder.toString();
    }

    public User() {}

    public User(String userId, String userName, String avatarKey, double balance, String phoneNum, String qqNo, String wechatId)
    {
        this.userId = userId;
        this.userName = userName;
        this.avatarKey = avatarKey;
        this.balance = balance;
        this.phoneNum = phoneNum;
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
        if (!StringUtils.isNullOrEmpty(avatarUrl))
        {
            return avatarUrl;
        }
        else
        {
            if (StringUtils.isNullOrEmpty(avatarKey))
            {
                avatarUrl = avatarUrlPrefix + "unknown.jpg";
            }
            else
            {
                avatarUrl = avatarUrlPrefix + avatarKey;
            }
            return avatarUrl;
        }

//        return avatarUrlPrefix + avatarKey;
//        return avatarKey;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
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
}
