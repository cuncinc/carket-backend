package chunson.cc.carket.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

@Component
public class FileUtils
{
    private static final String AVATAR_PRE = "avatar/";
//    private static final String ASSETS_PRE = "assets/";
    private static final String COVER_PRE = "cover/";
    private static String resourcePath;
    private static String walletPath;

    public FileUtils(@Value("${file.resourcePath}") String resourcePath, @Value("${file.walletPath}") String walletPath)
    {
        FileUtils.resourcePath = resourcePath;
        FileUtils.walletPath = walletPath;
    }

    public static String getWalletPath()
    {
        return walletPath;
    }

    public static String storeAvatar(MultipartFile file)
    {
        return storeFile(file, AVATAR_PRE);
    }

    public static String storeCover(MultipartFile file)
    {
        return storeFile(file, COVER_PRE);
    }

//    public static String storeAssets(MultipartFile file) throws IOException
//    {
////        return storeFile(file, ASSETS_PRE);
//        return IpfsUtils.upload(file.getBytes());
//    }

    private static String storeFile(MultipartFile file, String pre)
    {
        String name = file.getOriginalFilename();
        assert name != null;
        String type = name.substring(name.lastIndexOf('.'));
        String md5 = getFileHash(file);
        String route = pre + md5 + type;
        String filename = resourcePath + route;
        File storedFile = new File(filename);
        try
        {
            file.transferTo(storedFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return route;
    }

    public static String getFileHash(MultipartFile file)
    {
        try
        {
            //获取文件的byte信息
            byte[] uploadBytes = file.getBytes();
            // 拿到一个MD5转换器
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            //转换为16进制
            return new BigInteger(1, digest).toString(16);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
