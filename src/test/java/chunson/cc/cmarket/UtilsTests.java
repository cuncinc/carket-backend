package chunson.cc.cmarket;

import chunson.cc.cmarket.utils.PasswordUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UtilsTests
{
    @Test
    void testmd5()
    {
        String md5 = PasswordUtils.md5Password("chunson");
    }
}
