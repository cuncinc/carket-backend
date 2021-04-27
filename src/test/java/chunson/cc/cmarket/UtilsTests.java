package chunson.cc.cmarket;

import chunson.cc.cmarket.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UtilsTests
{
    @Test
    void testmd5()
    {
        String md5 = StringUtils.md5Password("chunson");
    }
}
