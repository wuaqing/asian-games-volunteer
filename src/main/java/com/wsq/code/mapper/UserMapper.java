package com.wsq.code.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsq.code.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wsq
 * @since 2021-06-16
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     *
     * @description: 根据学号和密码查询是否存在该用户
     * @author wsq
     * @since 2021/6/18 16:08
     * @param studentId: 学号/工号
     * @param MD5Password: 密码
     * @return com.wsq.code.entity.User
    */
    User selectByStudentIdAndPassword(String studentId, String MD5Password);

    /**
     *
     * @description: 根据学号查询该用户是否存在，存在则返回用户信息，不存在则为null
     * @author wsq
     * @since 2021/6/21 11:14
     * @param studentId: 学号
     * @return com.wsq.code.entity.User
    */
    User selectByStudentId(String studentId);

    /**
     *
     * @description: 通过学号获取个数
     * @author wsq
     * @since 2021/6/30 20:15
     * @param studentId: 学号
     * @return int
    */
    int selectByStudentIdCount(String studentId);

    /**
     *
     * @description: 管理员根据姓名查找用户并分页
     * @author wsq
     * @since 2021/7/3 13:43
     * @param name: 姓名
     * @return java.util.List<com.wsq.code.entity.User>
    */
    IPage<User> selectUserByName(Page<User> page, String name);

    /**
     *
     * @description: 查看所有用户并分页
     * @author wsq
     * @since 2021/7/4 8:47
     * @param page:
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.wsq.code.entity.User>
    */
    IPage<User> selectAllUser(Page<User> page);
}
