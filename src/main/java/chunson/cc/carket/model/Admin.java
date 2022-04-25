package chunson.cc.carket.model;

public class Admin
{
    private String adminName;
    private String pswHash;

    public Admin(){}

    public Admin(String adminName, String pswHash)
    {
        this.adminName = adminName;
        this.pswHash = pswHash;
    }

    public String getAdminName()
    {
        return adminName;
    }

    public void setAdminName(String adminName)
    {
        this.adminName = adminName;
    }

    public String getPswHash()
    {
        return pswHash;
    }

    public void setPswHash(String pswHash)
    {
        this.pswHash = pswHash;
    }
}
