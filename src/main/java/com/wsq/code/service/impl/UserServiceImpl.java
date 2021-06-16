package com.wsq.code.service.impl;

import com.wsq.code.entity.User;
import com.wsq.code.mapper.UserMapper;
import com.wsq.code.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
