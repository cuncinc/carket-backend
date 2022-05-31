package chunson.cc.carket.model;

import java.sql.Date;

public class Event
{
    private Long eid;
    private String from;
    private String to;
    private Long tokenId;
    private Integer amount;
    private String type;
    private String txHash;
    private String timestamp;

    public static final String NullAddress = "0x0000000000000000000000000000000000000000";

    public Event(String type, String from, String to, Long tokenId, Integer amount, String txHash)
    {
        this.from = from;
        this.to = to;
        this.tokenId = tokenId;
        this.amount = amount;
        this.type = type;
        this.txHash = txHash;
    }

    public Long getEid()
    {
        return eid;
    }

    public void setEid(Long eid)
    {
        this.eid = eid;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public Long getTokenId()
    {
        return tokenId;
    }

    public void setTokenId(Long tokenId)
    {
        this.tokenId = tokenId;
    }

    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTxHash()
    {
        return txHash;
    }

    public void setTxHash(String txHash)
    {
        this.txHash = txHash;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }
}
