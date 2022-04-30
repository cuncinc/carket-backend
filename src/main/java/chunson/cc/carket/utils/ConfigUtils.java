package chunson.cc.carket.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"config/test-config.properties"})
public class ConfigUtils
{
    private static String resourceUrlPre;
    private static String ipfsPrefix;

    @Value("${my.urlPrefix}")
    public void setResourceUrlPre(String resourceUrlPre)
    {
        ConfigUtils.resourceUrlPre = resourceUrlPre;
    }

    @Value("${my.ipfsPrefix}")
    public void setIpfsPrefix(String ipfsPrefix)
    {
        ConfigUtils.ipfsPrefix = ipfsPrefix;
    }

    public static String getResourceUrlPre()
    {
        return resourceUrlPre;
    }

    public static String getIpfsPrefix()
    {
        return ipfsPrefix;
    }
}