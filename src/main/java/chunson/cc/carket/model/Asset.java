package chunson.cc.carket.model;

import chunson.cc.carket.utils.ConfigUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Asset
{
    private Long aid;         //主键
    private Long tokenId;     //在链上的tokenId
    private String name;        //title
    private String type;
    private String desc;
    private String createTime;
    private String owner;       //目前拥有者的地址
    private String creator;     //作者的地址
    private Integer rate;        //二级市场作者的收益率，0-10
    private String hash;        //文件哈希
    private String jsonCid;     //IPFS json文本的CID
    private String ipfsCid;       //文件的IPFS的CID
    private String label;       //标签
    private Integer price;       //价格，只能是整数，货币是VNT
    private Integer clickCount;  //点击数
    private Integer favoriteCount;  //点击数
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
        map.put("cid", getIpfsCid());
        map.put("creator", getCreator());
        map.put("rate", getRate());

        Gson gson = new Gson();

        return gson.toJson(map);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAid(Long aid)
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

    public void setIpfsCid(String ipfsCid)
    {
        this.ipfsCid = ipfsCid;
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

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public Long getAid()
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

    public Integer getPrice()
    {
        return price;
    }

    public Integer getClickCount()
    {
        return clickCount;
    }

    public String getState()
    {
        return state;
    }

    public String getIpfsLink()
    {
        if (ipfsCid != null)
            return ConfigUtils.getIpfsPrefix() + ipfsCid;
        return null;
    }

    public Integer getFavoriteCount()
    {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount)
    {
        this.favoriteCount = favoriteCount;
    }

    public void addFavorite()
    {
        favoriteCount++;
    }

    public void subFavorite()
    {
        if (favoriteCount > 0)
            favoriteCount--;
    }

    public String getHash()
    {
        return hash;
    }

    public String getIpfsCid()
    {
        return ipfsCid;
    }

    public Long getTokenId()
    {
        return tokenId;
    }

    public Integer getRate()
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
