package com.wsq;

import com.wsq.code.mapper.UserMapper;
import com.xiaoTools.core.regular.validation.Validation;
import com.xiaoTools.core.result.Result;
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
        System.out.println(Validation.isMobile("999"));
    }

    @Test
    void contextLoads_4() {
        String userRegister = "女n";
        //判断性别
        if (userRegister != "女" && userRegister != "男"){
            System.out.println("修改失败，性别请填写\"女\"或者\"男\"");
        }
        System.out.println("修改成功");
    }

}
