package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {


    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Redis Set
     */
    public void setData(String key, String value) {
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        stringStringValueOperations.set(key, value);
    }

    /**
     * Redis get
     */
    public String getData(String key) {
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        String refreshToken = stringStringValueOperations.get(key);
        return refreshToken;
    }

    /**
     * Redis 유효 기간 설정
     */
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        stringStringValueOperations.set(key, value, expireDuration);
    }

    /**
     * Redis delete
     */
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
