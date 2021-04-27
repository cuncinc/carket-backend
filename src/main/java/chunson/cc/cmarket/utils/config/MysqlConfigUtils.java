package chunson.cc.cmarket.utils.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"config/db-config.properties"}, encoding = "UTF-8")
public class MysqlConfigUtils
{
    private static String host;
    private static int    port;
    private static String username;
    private static String password;

    @Value("${mysql.host}")
    public void setHost(String host)
    {
        MysqlConfigUtils.host = host;
    }

    @Value("${mysql.port}")
    public void setPort(int port)
    {
        MysqlConfigUtils.port = port;
    }

    @Value("${mysql.username}")
    public void setUsername(String username)
    {
        MysqlConfigUtils.username = username;
    }

    @Value("${mysql.password}")
    public void setPassword(String password)
    {
        MysqlConfigUtils.password = password;
    }

    public static String getHost()
    {
        return host;
    }

    public static int getPort()
    {
        return port;
    }

    public static String getUsername()
    {
        return username;
    }

    public static String getPassword()
    {
        return password;
    }
}
