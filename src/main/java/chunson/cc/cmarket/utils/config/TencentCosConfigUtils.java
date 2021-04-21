package chunson.cc.cmarket.utils.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"config/cos-config.properties"}, encoding = "UTF-8")
public class TencentCosConfigUtils
{
    private static String secretId;
    private static String secretKey;
    private static String region;
    private static String bucketName;

    @Value("${tencent_cos_secret_id}")
    public void setSecretId(String secretId)
    {
        TencentCosConfigUtils.secretId = secretId;
    }

    @Value("${tencent_cos_secret_key}")
    public void setSecretKey(String secretKey)
    {
        TencentCosConfigUtils.secretKey = secretKey;
    }

    @Value("${tencent_cos_region}")
    public void setRegion(String region)
    {
        TencentCosConfigUtils.region = region;
    }

    @Value("${tencent_cos_bucket_name}")
    public void setBucketName(String bucketName)
    {
        TencentCosConfigUtils.bucketName = bucketName;
    }

    public static String getSecretId()
    {
        return secretId;
    }

    public static String getSecretKey()
    {
        return secretKey;
    }

    public static String getRegion()
    {
        return region;
    }

    public static String getBucketName()
    {
        return bucketName;
    }
}
