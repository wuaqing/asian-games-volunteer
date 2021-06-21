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
    User Login(String studentId, String password);

}
