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
@RequestMapping("/admin")
public class AdminController {

    /**
     * Service 接口
     */
    @Resource
    private UserService userService;

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


}
