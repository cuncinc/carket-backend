package chunson.cc.carket.model;

public class Report
{
    private Long rid;
    private String from;
    private Long tokenId;
    private String why;
    private String state;
    private String creatTime;

    public Report() {}

    public Long getRid()
    {
        return rid;
    }

    public void setRid(Long rid)
    {
        this.rid = rid;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public Long getTokenId()
    {
        return tokenId;
    }

    public void setTokenId(Long tokenId)
    {
        this.tokenId = tokenId;
    }

    public String getWhy()
    {
        return why;
    }

    public void setWhy(String why)
    {
        this.why = why;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCreatTime()
    {
        return creatTime;
    }

    public void setCreatTime(String creatTime)
    {
        this.creatTime = creatTime;
    }
}
