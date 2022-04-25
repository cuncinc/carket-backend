package chunson.cc.carket.model;

import chunson.cc.carket.utils.TimeUtils;

public class AuditRecord
{
    private int auditId;
    private String adminName;
    private int aid;
    private String checkTime;
    private String state;
    private String reason;

    public AuditRecord() {}

    public AuditRecord(String adminName, int aId, String state, String reason)
    {
        this.adminName = adminName;
        this.aid = aId;
        this.state = state;
        this.reason = reason;
    }

    public int getAuditId()
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

    public int getAid()
    {
        return aid;
    }

    public void setAid(int aid)
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
