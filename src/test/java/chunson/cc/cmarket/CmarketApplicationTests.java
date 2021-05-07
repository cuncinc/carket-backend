package chunson.cc.cmarket;

import chunson.cc.cmarket.utils.COSUtils;
import chunson.cc.cmarket.utils.SmsUtils;
import chunson.cc.cmarket.utils.TestConfigUtils;
import chunson.cc.cmarket.utils.config.MysqlConfigUtils;
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
        System.out.println(MysqlConfigUtils.getHost());
        System.out.println(MysqlConfigUtils.getPort());
        System.out.println(MysqlConfigUtils.getUsername());
        System.out.println(MysqlConfigUtils.getPassword());
    }
}
