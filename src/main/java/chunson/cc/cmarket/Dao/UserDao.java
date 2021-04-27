package chunson.cc.cmarket.Dao;

import chunson.cc.cmarket.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao
{
    @Autowired
    private JdbcTemplate template;

    public void insert(User user)
    {
        template.update("INSERT INTO `user`(UserName, `Password`, AvatarKey, PhoneNo, QQNo, WechatId) VALUES(?, ?, ?, ?, ?, ?);",
                user.getUserName(),
                user.getPassword(),
                user.getAvatarKey(),
                user.getPhoneNum(),
                user.getQqNo(),
                user.getWechatId());
    }

    public User getUserById(String userId)
    {
        return template.queryForObject("SELECT * FROM `user` WHERE UserId = ?;", (rs, rowNum) -> {
            if (null == rs)
            {
                return null;
            }
            String id = rs.getString("UserId");
            String username = rs.getString("UserName");
            String avatarKey = rs.getString("AvatarKey");
            double balance = rs.getDouble("balance");
            String phone = rs.getString("PhoneNo");
            String qq = rs.getString("QQNo");
            String wechatId = rs.getString("WechatId");
            User user = new User(id, username, avatarKey, balance, phone, qq, wechatId);

            return user;
        }, userId);
    }

    public User getUserByPhone(String phoneNum)
    {
        return template.queryForObject("SELECT * FROM `user` WHERE PhoneNum = ?;", (rs, rowNum) -> {
            if (null == rs)
            {
                return null;
            }
            String id = rs.getString("UserId");
            String username = rs.getString("UserName");
            String avatarKey = rs.getString("AvatarKey");
            double balance = rs.getDouble("balance");
            String phone = rs.getString("PhoneNo");
            String qq = rs.getString("QQNo");
            String wechatId = rs.getString("WechatId");
            User user = new User(id, username, avatarKey, balance, phone, qq, wechatId);

            return user;
        }, phoneNum);
    }

    public User getUser(String phoneNum, String password)
    {
        try
        {
            return template.queryForObject("SELECT * FROM `user` WHERE PhoneNo = ? AND `Password` = ?;", (rs, rowNum) -> {
                String id = rs.getString("UserId");
                String username = rs.getString("UserName");
                String avatarKey = rs.getString("AvatarKey");
                double balance = rs.getDouble("balance");
                String phone = rs.getString("PhoneNo");
                String qq = rs.getString("QQNo");
                String wechatId = rs.getString("WechatId");
                User user = new User(id, username, avatarKey, balance, phone, qq, wechatId);

                return user;
            }, phoneNum, password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
