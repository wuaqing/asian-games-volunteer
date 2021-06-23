package com.wsq.code.controller;


import com.wsq.code.entity.User;
import com.wsq.code.entity.user.UserLogin;
import com.wsq.code.entity.user.UserRegister;
import com.wsq.code.entity.user.UserUpdate;
import com.wsq.code.service.UserService;
import com.wsq.code.util.BeanUtil;
import com.xiaoTools.core.IdUtil.IdUtil;
import com.xiaoTools.core.IdUtil.uuid.UUID;
import com.xiaoTools.core.result.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/user")
public class UserController {

    /**
     * Service 接口
     */
    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String , Object> redisTemplate;

    /**
     *
     * @description: 用户信息修改
     * @author wsq
     * @since 2021/6/22 9:18
     * @param token: 识别令牌
     * @param userUpdate: 用户修改信息实体类
     * @return com.xiaoTools.core.result.Result
    */
    @PutMapping("/update")
    public Result update(@RequestHeader(value = "Token") String token,@RequestBody UserUpdate userUpdate){
        return userService.update(token, userUpdate,"/user/update");
    }

    /**
     *
     * @description: 用户注册
     * @author wsq
     * @since 2021/6/21 11:27
     * @param userRegister: 用户注册实体类
     * @return com.xiaoTools.core.result.Result
    */
    @PostMapping("/register")
    public Result register(UserRegister userRegister){
        //查询该用户是否存在
        User isUser = userService.selectUser(userRegister.getStudentId());
        //用户不存在，可以注册
        if (isUser == null){
            //用户注册
            userService.register(userRegister);
            //注册完成再次检测用户是否存在
            isUser = userService.selectUser(userRegister.getStudentId());
            if (isUser != null){
                return new Result().result200("注册成功","/user/register");
            }
            return new Result().result403("出错了","/user/register");
        }
        return new Result().result403("该用户已经存在","/user/register");
    }

    /**
     *
     * @description: 使用学号和密码实现用户登录
     * @author wsq
     * @since 2021/6/18 15:21
     * @param userLogin: 用户登录实体类
     * @return com.xiaoTools.core.result.Result
    */
    @PostMapping("userLogin")
    public Result userLogin(UserLogin userLogin) {
        //调用 Service 查询登录信息是否正确，正确返回用户信息，错误返回null
        User user = userService.login(userLogin);
        //用户信息错误
        if (user == null) {
            return new Result().result403("登录信息填写错误，请重新输入","/user/userLogin");
        }
        String key = IdUtil.fastUUID();
        redisTemplate.opsForValue().set(key,user,7, TimeUnit.DAYS);
        Map<String,String> result = new HashMap<>(2);
        result.put("info","登录成功");
        result.put("token",key);
        //用户信息正确
        return new Result().result200(result,"/user/userLogin");
    }

}
