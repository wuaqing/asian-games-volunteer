package com.wsq.code.service;

import com.wsq.code.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wsq.code.entity.user.UserLogin;
import com.wsq.code.entity.user.UserRegister;
import com.wsq.code.entity.user.UserUpdate;
import com.xiaoTools.core.result.Result;

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
}
