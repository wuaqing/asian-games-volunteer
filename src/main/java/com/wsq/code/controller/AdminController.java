package com.wsq.code.controller;


import com.wsq.code.entity.User;
import com.wsq.code.entity.user.UserLogin;
import com.wsq.code.service.UserService;
import com.xiaoTools.core.IdUtil.IdUtil;
import com.xiaoTools.core.result.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wsq
 * @since 2021-06-16
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    /**
     * UserService 接口
     */
    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     *
     * @description: 管理员查看所有用户
     * @author wsq
     * @since 2021/6/28 11:33

     * @return com.xiaoTools.core.result.Result
    */
    @GetMapping("/selectAllUser")
    public Result selectAllUser(){
        return userService.selectAllUser("/user/selectAllUser");
    }

    /**
     *
     * @description: 使用工号和密码实现管理员登录
     * @author wsq
     * @since 2021/6/18 14:01
     * @param userLogin: 用户登录实体类
     * @return com.xiaoTools.core.result.Result
    */
    @PostMapping("adminLogin")
    public Result adminLogin(UserLogin userLogin) {
        //调用 Service 查询登录信息是否正确，正确返回用户信息，错误返回null
        User user = userService.login(userLogin);
        //用户信息错误
        if (user == null) {
            return new Result().result403("登录信息填写错误，请重新输入","/user/adminLogin");
        }
        //用户是管理员
        if (user.getRole().equals("admin")) {
            //定义一个无重复的键
            String key = IdUtil.fastUUID();
            //将键和 user 放入 redis 中，设置时间为7天
            redisTemplate.opsForValue().set(key,user,7, TimeUnit.DAYS);
            // result 中包含两个信息：登陆成功；和 user 对应的 key
            Map<String,String> result = new HashMap<>(2);
            result.put("info","登录成功");
            result.put("token",key);
            //将信息传给前端
            return new Result().result200(result,"/user/adminLogin");
        }
        //用户信息不是管理员
        return new Result().result403("您不是管理员，请选择用户登录","/user/adminLogin");
    }


}
