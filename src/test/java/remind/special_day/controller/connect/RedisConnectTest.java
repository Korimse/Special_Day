package remind.special_day.controller.connect;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RedisConnectTest {

    @Autowired
    private final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();


    @Test
    void RedisConnectionTest() {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set("yellow", "banana");
        vop.set("red", "apple");

        String yellow = vop.get("yellow");
        String red = vop.get("red");

        assertThat(yellow).isEqualTo("banana");
        assertThat(red).isEqualTo("apple");
    }

}