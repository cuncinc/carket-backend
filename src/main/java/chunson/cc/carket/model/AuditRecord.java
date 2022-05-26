package chunson.cc.carket.model;

import chunson.cc.carket.utils.TimeUtils;

public class AuditRecord
{
    private Integer auditId;
    private String adminName;
    private Long aid;
    private String checkTime;
    private String state;
    private String reason;

    public AuditRecord() {}

    public AuditRecord(String adminName, long aid, String state, String reason)
    {
        this.adminName = adminName;
        this.aid = aid;
        this.state = state;
        this.reason = reason;
    }

    public Integer getAuditId()
    {
        return auditId;
    }

    public String getAdminName()
    {
        return adminName;
    }

    public void setAdminName(String adminName)
    {
        this.adminName = adminName;
    }

    public Long getAid()
    {
        return aid;
    }

    public void setAid(long aid)
    {
        this.aid = aid;
    }

    public String getCheckTime()
    {
        return TimeUtils.humanTime(checkTime);
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }
}
