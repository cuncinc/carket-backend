package chunson.cc.carket.model;

import chunson.cc.carket.utils.ConfigUtils;
import chunson.cc.carket.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class Asset
{
    private int aid;
    private String name;
    private String type;
    private String address;
    private String createTime;
    private String creator;
    private String desc;
    private String hash;
    private String route;
    private String label;
    private double price;
    private int clickCount;
    private String state;

    public Asset()
    {
    }

    public Asset(String name, String type, String creator, String desc, String label)
    {
        this.name = name;
        this.type = type;
        this.creator = creator;
        this.desc = desc;
        this.label = label;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAid(int aid)
    {
        this.aid = aid;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public void setHash(String hash)
    {
        this.hash = hash;
    }

    public void setRoute(String route)
    {
        this.route = route;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public void setClickCount(int clickCount)
    {
        this.clickCount = clickCount;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getName()
    {
        return name;
    }

    public int getAid()
    {
        return aid;
    }

    public String getType()
    {
        return type;
    }

    public String getAddress()
    {
        return address;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public String getCreator()
    {
        return creator;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getLabel()
    {
        return label;
    }

    public double getPrice()
    {
        return price;
    }

    public int getClickCount()
    {
        return clickCount;
    }

    public String getState()
    {
        return state;
    }

    public String getLink()
    {
        if (route != null)
            return ConfigUtils.getIpfsPrefix() + route;
        return null;
    }

    public String getHash()
    {
        return hash;
    }

    public String getRoute()
    {
        return route;
    }
}
