package com.wsq;

import com.wsq.code.mapper.UserMapper;
import com.xiaoTools.core.regular.validation.Validation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest(classes = AsianGamesVolunteerApplication.class)
class AsianGamesVolunteerApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String , Object> redisTemplate;

    @Test
    void contextLoads_0() {
        System.out.println(userMapper.selectByStudentIdAndPassword("1", "1"));
    }

    @Test
    void contextLoads_1() {
        redisTemplate.opsForValue().set("WSQ","this is WSQ set in");
    }

    @Test
    void contextLoads_2() {
        System.out.println(redisTemplate.opsForValue().get("WSQ"));
    }

    @Test
    void contextLoads_3() {
        System.out.println(Validation.isMobile("12345678901"));
        System.out.println(Validation.isMobile("17605818915"));
    }

}
