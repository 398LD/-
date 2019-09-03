package com.kexun;

import com.kexun.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SsoRedisApplicationTests {

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @Test
    public void contextLoads() {

        User lidong = new User(1, "lidong", "123");
        redisTemplate.opsForValue().set("eee", lidong);

        System.out.println(redisTemplate.opsForValue().get("eee"));

    }

}
