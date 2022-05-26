package chunson.cc.carket.model;

import chunson.cc.carket.utils.ConfigUtils;
import chunson.cc.carket.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class ShowAsset extends Asset
{
    private String ownerName;
    private String avatarRoute;

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getAvatarRoute()
    {
        return avatarRoute;
    }

    public void setAvatarRoute(String avatarRoute)
    {
        this.avatarRoute = avatarRoute;
    }

    public String getAvatarLink()
    {
        if (avatarRoute != null)
            return ConfigUtils.getResourceUrlPre() + avatarRoute;
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
        map.put("link", getLink());
        map.put("type", getType());
        map.put("label", getLabel());
        map.put("ownerName", ownerName);
        map.put("avatarLink", getAvatarLink());
        map.put("state", getState());
        map.put("price", getPrice()+"");
        map.put("rate", getRate()+"");

        return map;
    }
}
