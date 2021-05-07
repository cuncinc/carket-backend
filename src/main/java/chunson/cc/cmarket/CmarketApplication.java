package chunson.cc.cmarket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CmarketApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(CmarketApplication.class, args);
    }

}
