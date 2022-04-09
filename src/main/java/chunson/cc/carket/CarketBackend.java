package chunson.cc.carket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CarketBackend
{
    public static void main(String[] args)
    {
        SpringApplication.run(CarketBackend.class, args);
    }
}
