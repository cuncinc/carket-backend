package chunson.cc.carket.model;

import chunson.cc.carket.utils.ConfigUtils;

public class User
{
    private String address;
    private String username;
    private String avatarRoute;
    private String coverRoute;
    private String joinedTime;
    private String email;
    private String bio;

    public User(){}

    public User(String address, String username, String avatarLink, String joinedTime, String email, String bio)
    {
        this.address = address;
        this.username = username;
        this.avatarRoute = avatarLink;
        this.joinedTime = joinedTime;
        this.email = email;
        this.bio = bio;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getAvatarLink()
    {
        return ConfigUtils.getResourceUrlPre() + avatarRoute;
    }

    public String getAvatarRoute()
    {
        return avatarRoute;
    }

    public void setAvatarRoute(String avatarRoute)
    {
        this.avatarRoute = avatarRoute;
    }

    public String getCoverLink()
    {
        return ConfigUtils.getResourceUrlPre() + coverRoute;
    }

    public String getCoverRoute()
    {
        return coverRoute;
    }

    public void setCoverRoute(String coverRoute)
    {
        this.coverRoute = coverRoute;
    }

    public String getJoinedTime()
    {
        return joinedTime;
    }

    public void setJoinedTime(String joinedTime)
    {
        this.joinedTime = joinedTime;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }
}
