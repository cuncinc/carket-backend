package chunson.cc.carket.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"config/test-config.properties"})
public class TestConfigUtils
{
    static String myPhoneNum;

    @Value("${my.phone}")
    public void setMyPhoneNum(String myPhoneNum)
    {
        TestConfigUtils.myPhoneNum = myPhoneNum;
    }

    public static String getMyPhoneNum()
    {
        return myPhoneNum;
    }
}