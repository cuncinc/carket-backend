package chunson.cc.carket.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

@Component
public class PswUtils
{
    private static byte[] walletKey;

    public PswUtils(@Value("${encrypt.walletKey}") String walletKey)
    {
        PswUtils.walletKey = walletKey.getBytes();
    }

    public static String hashPassword(String plain_password)
    {
        return BCrypt.hashpw(plain_password, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String password, String storedHash)
    {
        return BCrypt.checkpw(password, storedHash);
    }

//    public static String encryptWalletPsw(String plain) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
//    {
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        SecretKey key = new SecretKeySpec(walletKey, "AES");
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        byte[] encrypted = cipher.doFinal(plain.getBytes());
//        return Base64.getEncoder().encodeToString(encrypted);
//    }
//
//    public static String decryptWalletPsw(String encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
//    {
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        SecretKey key = new SecretKeySpec(walletKey, "AES");
//        cipher.init(Cipher.DECRYPT_MODE, key);
//        byte[] plain = cipher.doFinal(Base64.getDecoder().decode(encrypted));
//        return new String(plain);
//    }

    public static String randomPassword()
    {
        return new SecureRandom().ints(new SecureRandom().nextInt(10) + 10, 33, 122).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
