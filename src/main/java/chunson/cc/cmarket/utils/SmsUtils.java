package chunson.cc.cmarket.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;

public class SmsUtils
{
    static Config config;

    //加载阿里云短信发送配置
    static
    {
        config = new Config();
        config.accessKeyId = ConfigUtils.getAccessKeyId();
        config.accessKeySecret = ConfigUtils.getAccessKeySecrete();
    }

    static public boolean sendSms(String phoneNum, String smsValidateCode)
    {
        boolean state = false;
        try
        {
            Client client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phoneNum)
                    .setSignName(ConfigUtils.getSignName())
                    .setTemplateCode(ConfigUtils.getTemplateCode())
                    .setTemplateParam("{\"code\":\"" + smsValidateCode +"\"}");
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
}