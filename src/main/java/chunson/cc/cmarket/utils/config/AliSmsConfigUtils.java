package chunson.cc.cmarket.utils.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"config/sms-config.properties"}, encoding = "UTF-8")
public class AliSmsConfigUtils
{
    private static String AccessKeyId;
    private static String AccessKeySecrete;
    private static String SignName;
    private static String TemplateCode;

    @Value("${aliyun_sms_access_key.id}")
    public void setAccessKeyId(String accessKeyId)
    {
        AccessKeyId = accessKeyId;
    }

    @Value("${aliyun_sms_access_key.secrete}")
    public void setAccessKeySecrete(String accessKeySecrete)
    {
        AccessKeySecrete = accessKeySecrete;
    }

    @Value("${aliyun_sms.SignName}")
    public void setSignName(String signName)
    {
        SignName = signName;
    }

    @Value("${aliyun_sms.TemplateCode}")
    public void setTemplateCode(String templateCode)
    {
        TemplateCode = templateCode;
    }

    public static String getAccessKeyId()
    {
        return AccessKeyId;
    }

    public static String getAccessKeySecrete()
    {
        return AccessKeySecrete;
    }

    public static String getSignName()
    {
        return SignName;
    }

    public static String getTemplateCode()
    {
        return TemplateCode;
    }
}