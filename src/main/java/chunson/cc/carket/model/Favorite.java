package chunson.cc.carket.model;

public class Favorite
{
    private String address;
    private Long aid;
    private Integer flag;
    private String opTime;

    public Favorite(String address, Long aid)
    {
        this.address = address;
        this.aid = aid;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Long getAid()
    {
        return aid;
    }

    public void setAid(Long aid)
    {
        this.aid = aid;
    }

    public Integer getFlag()
    {
        return flag;
    }

    public void setFlag(Integer flag)
    {
        this.flag = flag;
    }

    public String getOpTime()
    {
        return opTime;
    }

    public void setOpTime(String opTime)
    {
        this.opTime = opTime;
    }
}
