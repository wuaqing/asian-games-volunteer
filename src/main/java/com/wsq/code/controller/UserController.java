package com.wsq.code.controller;


import com.wsq.code.entity.User;
import com.wsq.code.service.UserService;
import com.xiaoTools.core.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    /**
     *
     * @description: 用于用户注册
     * @author wsq
     * @since 2021/6/21 11:27
     * @param studentId: 学号
     * @param password: 密码
     * @param name: 姓名
     * @param sex: 性别
     * @param telephone: 电话号码
     * @param language: 会什么语言
     * @param job: 申请岗位
     * @param age: 年龄
     * @return com.xiaoTools.core.result.Result
    */
    @PostMapping("/register")
    public Result register(
            String studentId,String password,String name,String sex,String telephone,String language,String job,Integer age){
        User user = new User(null, studentId, password, name, sex, telephone, language, job, age, "user");
        //查询该用户是否存在
        User isUser = userService.selectUser(user.getStudentId());
        //用户不存在，可以注册
        if (isUser == null){
            //用户注册
            User register = userService.register(user);
            return new Result().result200(register,"/user/register");
        }
        return new Result().result403("该用户已经存在","/user/register");
    }


    /**
     *
     * @description: 使用工号和密码实现管理员登录
     * @author wsq
     * @since 2021/6/18 14:01
     * @param studentId: 工号
     * @param password: 密码
     * @return com.xiaoTools.core.result.Result
    */
    @PostMapping("adminLogin")
    public Result adminLogin(String studentId,String password) {
        //调用 Service 查询登录信息是否正确，正确返回用户信息，错误返回null
        User user = userService.login(studentId, password);
        //用户信息错误
        if (user == null) {
            return new Result().result403("登录信息填写错误，请重新输入","/user/adminLogin");
        }
        //用户是管理员
        if (user.getRole().equals("admin")) {
            return new Result().result200(user,"/user/adminLogin");
        }
        //用户信息不是管理员
        return new Result().result403("您不是管理员，请选择用户登录","/user/adminLogin");
    }

    /**
     *
     * @description: 使用学号和密码实现管理员登录
     * @author wsq
     * @since 2021/6/18 15:21
     * @param studentId: 学号
     * @param password: 密码
     * @return com.xiaoTools.core.result.Result
    */
    @PostMapping("userLogin")
    public Result userLogin(String studentId,String password) {
        //调用 Service 查询登录信息是否正确，正确返回用户信息，错误返回null
        User user = userService.login(studentId, password);
        //用户信息错误
        if (user == null) {
            return new Result().result403("登录信息填写错误，请重新输入","/user/userLogin");
        }
        //用户信息正确
        return new Result().result200(user,"/user/userLogin");
    }

}
