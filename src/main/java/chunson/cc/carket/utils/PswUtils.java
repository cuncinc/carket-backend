package chunson.cc.carket.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PswUtils
{
//    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
//
//    public static String md5Password(String text)
//    {
////        System.out.println(text);
//        text = salt(text);
////        System.out.println(text);
//        try
//        {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            text = byte2hex(md.digest(text.getBytes()));
//        }
//        catch (NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        }
////        System.out.println(text);
//
//        return text;
//    }
//
//    private static String salt(String text)
//    {
//        text = ".cmarket." + text + ".cmarket.";
//        return text;
//    }
//
//    private static String byte2hex(byte[] b)
//    {
//        StringBuilder resultSb = new StringBuilder();
//        for (byte value : b)
//        {
//            resultSb.append(byte2String(value));
//        }
//        return resultSb.toString();
//    }
//
//    private static String byte2String(byte b)
//    {
//        int n = b;
//        if (n < 0)
//            n = 256 + n;
//        int d1 = n / 16;
//        int d2 = n % 16;
//        return hexDigits[d1] + hexDigits[d2];
//    }


    public static String hashPassword(String plain_password)
    {
        return BCrypt.hashpw(plain_password, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String password, String storedHash)
    {
        return BCrypt.checkpw(password, storedHash);
    }
}
