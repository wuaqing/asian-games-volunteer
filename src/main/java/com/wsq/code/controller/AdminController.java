package com.wsq.code.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.wsq.code.entity.User;
import com.wsq.code.entity.user.AdminReleaseExam;
import com.wsq.code.entity.user.AdminUserUpdate;
import com.wsq.code.entity.user.UserLogin;
import com.wsq.code.service.UserService;
import com.xiaoTools.core.result.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
     * @description: 管理员发布考试
     * @author wsq
     * @since 2021/7/5 9:24
     * @param token:
     * @param releaseExam: 管理员发布考试内容实体类
     * @return com.xiaoTools.core.result.Result
    */
    @PostMapping("releaseExam")
    public Result releaseExam(@RequestHeader(value = "satoken")String token, @RequestBody AdminReleaseExam releaseExam){
        return userService.adminReleaseExam(releaseExam,"/admin/releaseExam");
    }

    /**
     *
     * @description: 管理员根据志愿者岗位查找用户并分页
     * @author wsq
     * @since 2021/7/3 13:36
     * @param token:
     * @param jobId: 志愿者岗位
     * @param current: 第几页
     * @param size: 一页几条
     * @return com.xiaoTools.core.result.Result
    */
    @SaCheckRole("admin")
    @GetMapping("selectUserByJob")
    public Result selectUserByJob(@RequestHeader(value = "satoken")String token,
                                   @RequestParam String jobId,
                                   @RequestParam Integer current,
                                   @RequestParam Integer size){
        return userService.adminSelectUserByJob(current,size, jobId,"/admin/selectUserByJob");
    }

    /**
     *
     * @description: 管理员根据姓名查找用户并分页
     * @author wsq
     * @since 2021/7/3 13:36
     * @param token:
     * @param name: 姓名
     * @param current: 第几页
     * @param size: 一页几条
     * @return com.xiaoTools.core.result.Result
    */
    @SaCheckRole("admin")
    @GetMapping("selectUserByName")
    public Result selectUserByName(@RequestHeader(value = "satoken")String token,
                                   @RequestParam String name,
                                   @RequestParam Integer current,
                                   @RequestParam Integer size){
        return userService.adminSelectUserByName(current,size, name,"/admin/selectUserByName");
    }

    /**
     *
     * @description: 管理员修改用户信息
     * @author wsq
     * @since 2021/7/3 11:02
     * @param token:
     * @param userUpdate: 管理员修改用户信息实体类
     * @return com.xiaoTools.core.result.Result
    */
    @SaCheckRole("admin")
    @PostMapping("/updateUser")
    public Result updateUser(@RequestHeader(value = "satoken")String token, @RequestBody AdminUserUpdate userUpdate){
        return userService.adminUpdateUser(userUpdate,"/admin/updateUser");
    }

    /**
     *
     * @description: 管理员删除用户
     * @author wsq
     * @since 2021/7/3 9:09
     * @param token:
     * @param id: 学生主键
     * @return com.xiaoTools.core.result.Result
    */
    @SaCheckRole("admin")
    @DeleteMapping("/removeUser")
    public Result removeUser(@RequestHeader(value = "satoken")String token,@RequestParam String id){
        return userService.adminremoveUser(id,"/admin/removeUser");
    }

    /**
     *
     * @description: 管理员批量添加用户
     * @author wsq
     * @since 2021/6/30 10:45
     * @param file: excel 表格（包含学号、姓名、电话号码）
     * @return com.xiaoTools.core.result.Result
    */
    @SaCheckRole("admin")
    @PostMapping("/addUser")
    public Result addUser(@RequestHeader(value = "satoken")String token,@RequestPart MultipartFile file) {
        return userService.adminAddUser(file,"/admin/addUser");
    }

    /**
     *
     * @description: 批量添加前下载 excel 模板
     * @author wsq
     * @since 2021/7/1 16:06
     * @param response:
     * @return void
    */
    @GetMapping("/addUserModule")
    public void addUserModule(HttpServletResponse response){
        userService.adminAddUserModule(response);

    }

    /**
     *
     * @description: 管理员查看所有用户并分页
     * @author wsq
     * @since 2021/6/28 11:33
     * @param token:
     * @param current: 第几页
     * @param size: 一页几条
     * @return com.xiaoTools.core.result.Result
    */
    @SaCheckRole("admin")
    @GetMapping("/selectAllUser")
    public Result selectAllUser(@RequestHeader(value = "satoken")String token,
                                @RequestParam Integer current, @RequestParam Integer size){
        return userService.selectAllUser(current,size, "/admin/selectAllUser");
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
            return new Result().result403("登录信息填写错误，请重新输入","/admin/adminLogin");
        }
        //用户是管理员
        if (user.getRole().equals("admin")) {
            StpUtil.login(user.getId());
            // result 中包含两个信息：登陆成功；和 user 对应的 key
            Map<String,String> result = new HashMap<>(2);
            result.put("info","登录成功");
            result.put("token",StpUtil.getTokenValue());
            //将信息传给前端
            return new Result().result200(result,"/admin/adminLogin");
        }
        //用户信息不是管理员
        return new Result().result403("您不是管理员，请选择用户登录","/admin/adminLogin");
    }


}
