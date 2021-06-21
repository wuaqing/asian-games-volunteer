package com.wsq.code.service;

import com.wsq.code.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param studentId: 学号/工号
     * @param password: 密码
     * @return com.wsq.code.entity.User
    */
    User login(String studentId, String password);

    /**
     *
     * @description: 用于用户注册
     * @author wsq
     * @since 2021/6/21 10:45
     * @param user: 用户信息
     * @return com.wsq.code.entity.User
    */
    User register(User user);

    /**
     *
     * @description: 根据学号查询该用户是否存在，存在则返回用户信息，不存在则为null
     * @author wsq
     * @since 2021/6/21 11:07
     * @param studentId: 学号
     * @return com.wsq.code.entity.User
    */
    User selectUser(String studentId);

}
