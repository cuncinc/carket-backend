package chunson.cc.cmarket.utils;

import chunson.cc.cmarket.utils.config.AliSmsConfigUtils;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.mysql.cj.util.StringUtils;
import org.springframework.cache.annotation.Cacheable;

import java.time.Duration;
import java.util.Random;

public class SmsUtils
{
    private static Config config;

    //加载阿里云短信发送配置
    static
    {
        config = new Config();
        config.accessKeyId = AliSmsConfigUtils.getAccessKeyId();
        config.accessKeySecret = AliSmsConfigUtils.getAccessKeySecrete();
    }

    private static boolean sendSms(String phone, String smsValidateCode)
    {
        boolean state = false;
        try
        {
            Client client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phone)
                    .setSignName(AliSmsConfigUtils.getSignName())
                    .setTemplateCode(AliSmsConfigUtils.getTemplateCode())
                    .setTemplateParam("{\"code\":\"" + smsValidateCode + "\"}");
            SendSmsResponse sendResp = client.sendSms(sendSmsRequest);
            String code = sendResp.body.code;
            if (!code.equals("OK"))
            {
                System.out.println("SMS 错误信息: " + sendResp.body.message);
                state = false;
            }
            state = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return state;
    }

    public static boolean sendSms(String phone)
    {
        long randomNum = Math.abs(new Random().nextLong() % (999999 + 1));
        String code = String.format("%6s", randomNum);

        if (sendSms(phone, code))
        {
            System.out.println(phone + ": " + code);
            RedisUtils.set(phone, code, 10);
            return true;
        }

        return false;
    }

    public static boolean validateSmsCode(String phone, String code)
    {
        String validate = RedisUtils.get(phone);
        if (!StringUtils.isNullOrEmpty(validate) && validate.equals(code))
        {
            RedisUtils.delete(phone);   //删除键值
            return true;
        }

        return false;
    }
}