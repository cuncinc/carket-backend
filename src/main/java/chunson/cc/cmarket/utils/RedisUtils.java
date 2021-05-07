package chunson.cc.cmarket.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Component
public class RedisUtils
{
    @Autowired
    public StringRedisTemplate tmp;

    private static StringRedisTemplate template;

    @PostConstruct
    public void init()
    {
        template = tmp;
    }

    public static void set(String key, String value, int minute)
    {
        Duration time = Duration.ofMinutes(minute);
        template.opsForValue().set(key, value, time);
    }

    public static String get(String key)
    {
        return template.opsForValue().get(key);
    }

    public static void delete(String key)
    {
        template.delete(key);
    }
}
