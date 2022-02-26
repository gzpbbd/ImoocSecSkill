package fit.hcp.imoocsecskill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.PostConstruct;

/**
 * @Date: 2022/02/04/10:32 AM
 */
@Configuration
public class Config {
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @PostConstruct
    public void configRedisTemplate() {
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
    }
}
