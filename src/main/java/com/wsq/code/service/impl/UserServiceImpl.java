package com.wsq.code.service.impl;

import com.wsq.code.entity.User;
import com.wsq.code.mapper.UserMapper;
import com.wsq.code.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsq.code.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wsq
 * @since 2021-06-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * Mapper 接口
     */
    @Resource
    private UserMapper userMapper;

    /**
     *
     * @description: 用于登录，核对数据库中是否有该学号（工号）和密码
     * @author wsq
     * @since 2021/6/18 15:24
     * @param studentId: 学号/工号
     * @param password: 密码
     * @return com.wsq.code.entity.User
    */
    @Override
    public User login(String studentId, String password) {
        //通过 student_id 和加密后的 password 来查询是否存在该用户。如果用户存在，返回用户信息，如果用户不存在，返回null
        User user = userMapper.selectByStudentIdAndPassword(studentId, MD5Utils.code(password));
        return user;
    }

    /**
     *
     * @description: 用于用户注册
     * @author wsq
     * @since 2021/6/21 11:16
     * @param user: 用户信息
     * @return com.wsq.code.entity.User
    */
    @Override
    public User register(User user) {
        //用户注册
        userMapper.insert(user);
        return user;
    }

    /**
     *
     * @description: 根据学号查询该用户是否存在，存在则返回用户信息，不存在则为null
     * @author wsq
     * @since 2021/6/21 11:08
     * @param studentId: 学号
     * @return com.wsq.code.entity.User
    */
    @Override
    public User selectUser(String studentId) {
        User user = userMapper.selectByStudentId(studentId);
        return user;
    }
}
