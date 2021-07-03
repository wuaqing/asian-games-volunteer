package com.wsq;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.wsq.code.entity.user.ExcelUser;
import com.wsq.code.mapper.UserMapper;
import com.wsq.code.util.FileUtil;
import com.xiaoTools.core.regular.validation.Validation;
import com.xiaoTools.core.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void contextLoads_5() {
        File file = new File("D:\\demo.xlsx");
        //导入的标题和说明的设置
        ImportParams params = new ImportParams();
//        //设置标题
//        params.setTitleRows(1);
//        //设置说明
//        params.setHeadRows(1);

        List<ExcelUser> users = ExcelImportUtil.importExcel(file, ExcelUser.class, params);
        for (ExcelUser user : users) {
            System.out.println(user);
        }
        System.out.println(users.size());
    }


    @Test
    void contextLoads_6() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        list.removeIf("2"::equals);

        list.forEach(System.out :: println);
    }

    @Test
    void contextLoads_7() {
        System.out.println(userMapper.selectByStudentIdCount("111"));

        System.out.println(Validation.isMobile("123123"));

    }
}
