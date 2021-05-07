package chunson.cc.cmarket.utils;

import chunson.cc.cmarket.utils.config.TencentCosConfigUtils;
import com.qcloud.cos.*;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.InputStream;

// 腾讯云对象存储
public class COSUtils
{
    private static final String AVATAR_PREFIX = "avatar/";
    private static final String GOODS_PIC_PREFIX = "goodsPic/";

    private static COSClient client;
    private static String bucketName;

    static
    {
        bucketName = TencentCosConfigUtils.getBucketName();

        String secretId = TencentCosConfigUtils.getSecretId();
        String secretKey = TencentCosConfigUtils.getSecretKey();
        String regionText = TencentCosConfigUtils.getRegion();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        Region region = new Region(regionText);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);

        client = new COSClient(cred, clientConfig);
    }

//    public static void test(String filePath)
//    {
//        String key = "1.jpg";
//        GetObjectRequest request = new GetObjectRequest(bucketName, key);
//        File file = new File(filePath);
//        ObjectMetadata metadata = client.getObject(request, file);
//        System.out.println(metadata.getContentLength());
//    }

    private static COSObject getCOSObject(String key)
    {
        try
        {
            return client.getObject(bucketName, key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static COSObjectInputStream getAvatarStream(String avatarKey)
    {
        String key = AVATAR_PREFIX + avatarKey;
        return getCOSObject(key).getObjectContent();
    }

    public static COSObjectInputStream getGoodsPicStream(String picKey)
    {
        String key = GOODS_PIC_PREFIX + picKey;
        return getCOSObject(key).getObjectContent();
    }

    private static boolean postCOSObject(InputStream input, String key)
    {
        ObjectMetadata metadata = new ObjectMetadata();
        // TODO: 2021/4/21 确定长度
//        metadata.setContentLength(10);                  // 必须限定长度，否则有可能会OOM
        metadata.setContentType("image/jpeg");
        PutObjectRequest request = new PutObjectRequest(bucketName, key, input, metadata);
        request.setStorageClass(StorageClass.Standard); // 设置存储类型

        // 若COS上已存在同样Key的对象，上传时则会进行覆盖
        // 若上传不成功，择抛出异常
        try
        {
            PutObjectResult result = client.putObject(request);
            System.out.println("COSObject ETag:  " + result.getETag());   // ETag是对象的MD5值
            return true;
        }
        catch (CosClientException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean uploadAvatar(InputStream input, String avatarKey)
    {
        String key = AVATAR_PREFIX + avatarKey;
        return postCOSObject(input, key);
    }

    public static boolean uploadGoodsPic(InputStream input, String picKey)
    {
        String key = GOODS_PIC_PREFIX + picKey;
        return postCOSObject(input, key);
    }

    private static boolean deleteCOSObject(String key)
    {
        try
        {
            client.deleteObject(bucketName, key);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteAvatar(String avatarKey)
    {
        String key = AVATAR_PREFIX + avatarKey;
        return deleteCOSObject(key);
    }

    public static boolean deleteGoodsPic(String picKey)
    {
        String key = GOODS_PIC_PREFIX + picKey;
        return deleteCOSObject(key);
    }
}
