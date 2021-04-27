package chunson.cc.cmarket;

import chunson.cc.cmarket.Dao.UserDao;
import chunson.cc.cmarket.model.User;
import chunson.cc.cmarket.utils.StringUtils;
import chunson.cc.cmarket.utils.TestConfigUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class MysqlTests
{
    @Autowired
    DataSource dataSource;

    @Autowired
    UserDao userDao;

    @Test
    void testConfig() throws SQLException
    {
        System.out.println(dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        connection.close();
    }

    @Test
    void testInsert()
    {
        User user = new User();
        user.setUserName("chunson");
        user.setPhoneNum(TestConfigUtils.getMyPhoneNum());
        String password = StringUtils.md5Password("chunson");
        user.setPassword(password);
        userDao.insert(user);
    }

    @Test
    void testQuery()
    {
        User user = userDao.getUserById("6");
        if (StringUtils.md5Password("666").equals(user.getPassword()))
        {
            System.out.println("yes");
        }
        else
        {
            System.out.println("no");
        }
        System.out.println(user.getPassword());
        System.out.println(user.getUserName());
    }

    @Test
    void testLogin()
    {
        User user = userDao.getUser(TestConfigUtils.getMyPhoneNum(), StringUtils.md5Password("chunson"));
        if (user != null)
            System.out.println(user.getUserName());
        else
            System.out.println("Can't login");
    }
}