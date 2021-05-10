package chunson.cc.cmarket.model;

import chunson.cc.cmarket.utils.config.CosConfig;
import com.mysql.cj.util.StringUtils;

public class DisplayGoods extends Goods
{
    String userName;
    String avatarKey;
    String avatarUrl;

    static final String AVATAR_URL_PREFIX;

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

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getAvatarKey()
    {
        return avatarKey;
    }

    public void setAvatarKey(String avatarKey)
    {
        this.avatarKey = avatarKey;
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

    public void setAvatarUrl(String avatarUrl) { }

    @Override
    public String toString()
    {
        return "DisplayGoods{" +
                "userName='" + userName + '\'' +
                ", avatarKey='" + avatarKey + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", goodsId=" + goodsId +
                ", sellerId=" + sellerId +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", picKey='" + picKey + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", state=" + state +
                ", releaseTime=" + releaseTime +
                '}';
    }
}