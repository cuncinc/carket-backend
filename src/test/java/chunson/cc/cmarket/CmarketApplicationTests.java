package chunson.cc.cmarket;

import chunson.cc.cmarket.utils.SmsUtils;
import chunson.cc.cmarket.utils.TestConfigUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CmarketApplicationTests
{

    @Test
    void contextLoads()
    {
        String phoneNum = TestConfigUtils.getMyPhoneNum();
        System.out.println(phoneNum);
        if (SmsUtils.sendSms(phoneNum, "123456"))
        {
            System.out.println("Yes");
        }
        else
        {
            System.out.println("No");
        }
    }
}
