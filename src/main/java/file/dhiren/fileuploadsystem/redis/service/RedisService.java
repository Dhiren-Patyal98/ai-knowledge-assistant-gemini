package file.dhiren.fileuploadsystem.redis.service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String,Object> redisTemplate;

    public RedisService(RedisTemplate<String,Object> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key , Object value,Duration ttl)
    {
        redisTemplate.opsForValue().set(key,value,ttl);
    }

    public Object get(String key)
    {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean delete(String key)
    {
        return redisTemplate.delete(key);
    }
}
