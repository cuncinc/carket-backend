package chunson.cc.carket.model;

import chunson.cc.carket.utils.ConfigUtils;
import chunson.cc.carket.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class ShowAsset extends Asset
{
//    private String ownerAddress;
    private String ownerName;
    private String ownerAvatarRoute;
//    private String creatorAddress;
    private String creatorName;
    private String creatorAvatarRoute;

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getCreatorName()
    {
        return creatorName;
    }

    public String getCreatorAvatarRoute()
    {
        return creatorAvatarRoute;
    }

    public String getOwnerAvatarRoute()
    {
        return ownerAvatarRoute;
    }

    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }

    public void setCreatorAvatarRoute(String creatorAvatarRoute)
    {
        this.creatorAvatarRoute = creatorAvatarRoute;
    }

    public void setOwnerAvatarRoute(String ownerAvatarRoute)
    {
        this.ownerAvatarRoute = ownerAvatarRoute;
    }

    private String getOwnerAvatar()
    {
        if (ownerAvatarRoute != null)
            return ConfigUtils.getResourceUrlPre() + ownerAvatarRoute;
        return null;
    }

    private String getCreatorAvatar()
    {
        if (creatorAvatarRoute != null)
            return ConfigUtils.getResourceUrlPre() + creatorAvatarRoute;
        return null;
    }

    public Map<String, String> marketing()
    {
        Map<String, String> map = new HashMap<>();
        map.put("aid", getAid() + "");
        map.put("name", getName());
        map.put("desc", getDesc());
        map.put("owner", getOwner());
        map.put("time", TimeUtils.humanTime(getCreateTime()));
        map.put("link", getIpfsLink());
        map.put("jsonLink", getJsonLink());
        map.put("type", getType());
        map.put("label", getLabel());
        map.put("ownerName", ownerName);
        map.put("creatorName", creatorName);
        map.put("ownerAvatar", getOwnerAvatar());
        map.put("creatorAvatar", getCreatorAvatar());
        map.put("favoriteCount", getFavoriteCount()+"");
        map.put("state", getState());
        map.put("price", getPrice()+"");
        map.put("rate", getRate()+"");

        return map;
    }
}
