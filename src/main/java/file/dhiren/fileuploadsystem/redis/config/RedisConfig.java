package file.dhiren.fileuploadsystem.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper)
    {
        RedisTemplate<String,Object> template = new RedisTemplate<>();

        StringRedisSerializer stringSerializer =  new StringRedisSerializer();

        GenericJacksonJsonRedisSerializer jsonSerializer = new GenericJacksonJsonRedisSerializer(objectMapper);

        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        template.setHashValueSerializer(jsonSerializer);
        template.setValueSerializer(jsonSerializer);


        template.afterPropertiesSet();

        return template;
    }
}
