package chunson.cc.carket.model;

import chunson.cc.carket.utils.ConfigUtils;
import chunson.cc.carket.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class AuditAsset extends Asset
{
    private String creatorName;
    private String avatarRoute;

    public String getCreatorName()
    {
        return creatorName;
    }

    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }

    public String getAvatarRoute()
    {
        return avatarRoute;
    }

    public String getAvatarLink()
    {
        if (avatarRoute != null)
            return ConfigUtils.getResourceUrlPre() + avatarRoute;
        return null;
    }

    public void setAvatarRoute(String avatarRoute)
    {
        this.avatarRoute = avatarRoute;
    }
    public Map<String, String> auditing()
    {
        Map<String, String> map = new HashMap<>();
        map.put("aid", getAid() + "");
        map.put("name", getName());
        map.put("desc", getDesc());
        map.put("creator", getCreator());
        map.put("time", TimeUtils.humanTime(getCreateTime()));
        map.put("link", getIpfsLink());
        map.put("type", getType());
        map.put("label", getLabel());
        map.put("creatorName", creatorName);
        map.put("avatarLink", getAvatarLink());
        map.put("state", getState());

        return map;
    }
}
