package com.wsq;

import com.wsq.code.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = AsianGamesVolunteerApplication.class)
class AsianGamesVolunteerApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(userMapper.selectByStudentIdAndPassword("1", "1"));
    }

}
