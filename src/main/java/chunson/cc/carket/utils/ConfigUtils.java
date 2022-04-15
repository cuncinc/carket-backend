package chunson.cc.carket.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"config/test-config.properties"})
public class ConfigUtils
{
    private static String resourceUrlPre;

    @Value("${my.urlPrefix}")
    public void setResourceUrlPre(String resourceUrlPre)
    {
        ConfigUtils.resourceUrlPre = resourceUrlPre;
    }

    public static String getResourceUrlPre()
    {
        return resourceUrlPre;
    }
}