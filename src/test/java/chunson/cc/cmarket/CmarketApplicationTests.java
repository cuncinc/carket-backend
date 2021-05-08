package chunson.cc.cmarket;

import chunson.cc.cmarket.utils.COSUtils;
import chunson.cc.cmarket.utils.config.MysqlConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
class CmarketApplicationTests
{
    // done!
//    @Test
//    void testSendSms()
//    {
//        String phoneNum = TestConfigUtils.getMyPhoneNum();
//        System.out.println(phoneNum);
//        if (SmsUtils.sendSms(phoneNum, "123456"))
//        {
//            System.out.println("Yes");
//        }
//        else
//        {
//            System.out.println("No");
//        }
//    }

    // done!
//    @Test
    void testGetCOSObject() throws IOException
    {
        InputStream stream = COSUtils.getAvatarStream("2.jpg");
        FileOutputStream out = new FileOutputStream("E:\\CC\\Desktop\\2.jpg");
        out.write(stream.readAllBytes());
    }


    // done!
//    @Test
    void testUploadCOSObject() throws FileNotFoundException
    {
        String filePath = "E:\\CC\\Desktop\\2.jpg";
        FileInputStream fis = new FileInputStream(filePath);
        boolean state = COSUtils.uploadAvatar(fis, "test.jpg");
        if (state)
        {
            System.out.println("done!");
        }
        else
        {
            System.out.println("fail!");
        }
    }

    // done!
//    @Test
    void testDeleteCOSObject()
    {
        if (COSUtils.deleteAvatar("test.jpg"))
        {
            System.out.println("done!");
        }
        else
        {
            System.out.println("fail!");
        }
    }

    @Test
    void testMysqlConfigUtils()
    {
        System.out.println(MysqlConfig.getHost());
        System.out.println(MysqlConfig.getPort());
        System.out.println(MysqlConfig.getUsername());
        System.out.println(MysqlConfig.getPassword());
    }
}
