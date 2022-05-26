package chunson.cc.carket.model;

import chunson.cc.carket.utils.ConfigUtils;
import chunson.cc.carket.utils.TimeUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Asset
{
    private int    aid;         //主键
    private long   tokenId;     //在链上的tokenId
    private String name;        //title
    private String type;
    private String desc;
    private String createTime;
    private String owner;       //目前拥有者的地址
    private String creator;     //作者的地址
    private int    rate;        //二级市场作者的收益率，0-10
    private String hash;        //文件哈希
    private String jsonCid;     //IPFS json文本的CID
    private String route;       //文件的IPFS的CID
    private String label;       //标签
    private int    price;       //价格，只能是整数，货币是VNT
    private int    clickCount;  //点击数
    private String state;       //状态：1.待审核 2.未通过 3.已通过 4.在链上 5.在流通 6.已下架

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

    public String toIpfsJson()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        map.put("desc", getDesc());
        map.put("cid", getRoute());
        map.put("creator", getCreator());
        map.put("rate", getRate());

        Gson gson = new Gson();

        return gson.toJson(map);
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

    public void setClickCount(int clickCount)
    {
        this.clickCount = clickCount;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setJsonCid(String jsonCid)
    {
        this.jsonCid = jsonCid;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public void setTokenId(long tokenId)
    {
        this.tokenId = tokenId;
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }

    public void setPrice(int price)
    {
        this.price = price;
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

    public long getTokenId()
    {
        return tokenId;
    }

    public int getRate()
    {
        return rate;
    }

    public String getJsonCid()
    {
        return jsonCid;
    }

    public String getOwner()
    {
        return owner;
    }
}
