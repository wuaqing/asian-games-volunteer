package com.wsq.code.service;

import com.wsq.code.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wsq.code.entity.user.*;
import com.xiaoTools.core.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wsq
 * @since 2021-06-16
 */
public interface UserService extends IService<User> {

    /**
     *
     * @description: 用于登录，核对数据库中是否有该学号（工号）和密码
     * @author wsq
     * @since 2021/6/18 14:55
     * @param userLogin: 用户登录实体类
     * @return com.wsq.code.entity.User
    */
    User login(UserLogin userLogin);

    /**
     *
     * @description: 用于用户注册
     * @author wsq
     * @since 2021/6/21 10:45
     * @param userRegister: 用户注册实体类
     * @param path: 地址
     * @return com.xiaoTools.core.result.Result
    */
    Result register(UserRegister userRegister,String path);

    /**
     *
     * @description: 根据学号查询该用户是否存在，存在则返回用户信息，不存在则为null
     * @author wsq
     * @since 2021/6/21 11:07
     * @param studentId: 学号
     * @return com.wsq.code.entity.User
    */
    User selectUser(String studentId);

    /**
     *
     * @description: 用户信息修改
     * @author wsq
     * @since 2021/6/22 9:28
     * @param token: 识别令牌
     * @param userUpdate: 用户修改实体类
     * @param path: 地址
     * @return com.xiaoTools.core.result.Result
    */
    Result update(String token, UserUpdate userUpdate,String path);

    /**
     *
     * @description: 密码修改
     * @author wsq
     * @since 2021/6/28 9:43
     * @param token:
     * @param updatePassword: 用户修改密码实体类,包含旧密码和新密码
     * @param path: 地址
     * @return com.xiaoTools.core.result.Result
    */
    Result password(String token, UpdatePassword updatePassword, String path);

    /**
     *
     * @description: 管理员查看所有用户并分页
     * @author wsq
     * @since 2021/6/28 11:32
     * @param current: 第几页
     * @param size: 一页几条
     * @param path:
     * @return com.xiaoTools.core.result.Result
    */
    Result selectAllUser(Integer current, Integer size, String path);

    /**
     *
     * @description: 管理员批量添加用户
     * @author wsq
     * @since 2021/6/30 16:53
     * @param file: excel 表格（包含学号、姓名、电话号码）
     * @param path:
     * @return com.xiaoTools.core.result.Result
    */
    Result adminAddUser(MultipartFile file, String path);

    /**
     *
     * @description: 批量添加前下载 excel 模板
     * @author wsq
     * @since 2021/7/1 16:08
     * @param response:
     * @return void
    */
    void adminAddUserModule(HttpServletResponse response);

    /**
     *
     * @description: 管理员删除用户
     * @author wsq
     * @since 2021/7/3 9:11
     * @param id: 学生主键
     * @param path:
     * @return com.xiaoTools.core.result.Result
    */
    Result adminremoveUser(String id, String path);

    /**
     * 
     * @description: 管理员修改用户信息
     * @author wsq
     * @since 2021/7/3 10:56
     * @param userUpdate: 管理员修改用户信息实体类
     * @param path: 
     * @return com.xiaoTools.core.result.Result
    */
    Result adminUpdateUser(AdminUserUpdate userUpdate, String path);

    /**
     *
     * @description: 管理员根据姓名查找用户并分页
     * @author wsq
     * @since 2021/7/3 13:37
     * @param name: 姓名
     * @param path:
     * @return com.xiaoTools.core.result.Result
    */
    Result adminSelectUserByName(Integer current,Integer size,String name, String path);
}
