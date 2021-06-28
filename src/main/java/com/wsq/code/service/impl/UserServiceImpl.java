package com.wsq.code.service.impl;

import com.wsq.code.entity.Job;
import com.wsq.code.entity.User;
import com.wsq.code.entity.user.UserLogin;
import com.wsq.code.entity.user.UserRegister;
import com.wsq.code.entity.user.UserUpdate;
import com.wsq.code.mapper.JobMapper;
import com.wsq.code.mapper.UserMapper;
import com.wsq.code.service.JobService;
import com.wsq.code.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsq.code.util.BeanUtil;
import com.wsq.code.util.MD5Utils;
import com.xiaoTools.core.regular.validation.Validation;
import com.xiaoTools.core.result.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Resource
    private JobService jobService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     *
     * @description: 用于登录，核对数据库中是否有该学号（工号）和密码
     * @author wsq
     * @since 2021/6/18 15:24
     * @param userLogin: 用户登录实体类
     * @return com.wsq.code.entity.User
    */
    @Override
    public User login(UserLogin userLogin) {
        //通过 student_id 和加密后的 password 来查询是否存在该用户。如果用户存在，返回用户信息，如果用户不存在，返回null
        User user = userMapper.selectByStudentIdAndPassword(userLogin.getStudentId(), MD5Utils.code(userLogin.getPassword()));
        return user;
    }

    /**
     *
     * @description: 用于用户注册
     * @author wsq
     * @since 2021/6/21 11:16
     * @param userRegister: 用户注册实体类
     * @param path: 地址
     * @return com.xiaoTools.core.result.Result
    */
    @Override
    public Result register(UserRegister userRegister,String path) {
        //查询该学号的用户是否存在
        User isUser = this.selectUser(userRegister.getStudentId());
        //用户存在，不能注册
        if (isUser != null){
            return new Result().result403("该用户已经存在",path);
        }
        //判断密码
        if(userRegister.getPassword() == null){
            return new Result().result403("注册失败，请填写密码",path);
        }
        //判断姓名
        if(userRegister.getName() == null){
            return new Result().result403("注册失败，请填写姓名",path);
        }
        //判断性别
        if ( ! userRegister.getSex().equals("女") && ! userRegister.getSex().equals("男")){
            return new Result().result403("注册失败，性别请填写\"女\"或者\"男\"",path);
        }
        //判断电话号码
        if ( ! Validation.isMobile(userRegister.getTelephone())) {
            return new Result().result403("注册失败，电话号码不正确",path);
        }
        //判断语言
        if (userRegister.getLanguage() == null){
            return new Result().result403("注册失败，请至少填写一门外语",path);
        }
        //判断工作岗位
        Job byId = jobService.getById(userRegister.getJob());
        if (byId == null){
            return new Result().result403("注册失败，请填写正确的工作岗位",path);
        }
        //判断年龄
        if (userRegister.getAge() < 18 || userRegister.getAge() > 100){
            return new Result().result403("注册失败，年龄需要18——100岁",path);
        }

        //用户注册
        //将用户注册实体类放入用户实体类中，角色为”user“
        User user = BeanUtil.copy(userRegister, User.class).setRole("user");
        //将用户密码加密
        user.setPassword(MD5Utils.code(userRegister.getPassword()));
        //用户注册
        userMapper.insert(user);
        //注册完成再次检测用户是否存在
        isUser = this.selectUser(userRegister.getStudentId());
        if (isUser != null){
            return new Result().result200("注册成功",path);
        }

        return new Result().result403("出错了",path);
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

    /**
     *
     * @description: 用户信息修改
     * @author wsq
     * @since 2021/6/22 9:30
     * @param token: 识别令牌
     * @param userUpdate: 用户修改实体类
     * @return com.wsq.code.entity.User
    */
    @Override
    public Result update(String token, UserUpdate userUpdate,String path) {
        //获取此 token 对应的 user
        User user = (User) redisTemplate.opsForValue().get(token);
        // user 为空，返回
        if (user == null) {
            return new Result().result200("用户未登录",path);
        }
        //判断性别
        if ( ! userUpdate.getSex().equals("女") && ! userUpdate.getSex().equals("男")){
            return new Result().result403("修改失败，性别请填写\"女\"或者\"男\"",path);
        }
        //判断电话号码
        if ( ! Validation.isMobile(userUpdate.getTelephone())) {
            return new Result().result403("修改失败，电话号码不正确",path);
        }
        //判断语言
        if (userUpdate.getLanguage() == null){
            return new Result().result403("修改失败，请至少填写一门外语",path);
        }
        //判断工作岗位
        Job byId = jobService.getById(userUpdate.getJob());
        if (byId == null){
            return new Result().result403("修改失败，请填写正确的工作岗位",path);
        }
        //判断年龄
        if (userUpdate.getAge() < 18 || userUpdate.getAge() > 100){
            return new Result().result403("修改失败，年龄需要18——100岁",path);
        }
        // 将 userUpdate 中的信息放入 User 实体类中，在数据库中修改此 id 的 user 的信息
        boolean update = this.updateById(BeanUtil.copy(userUpdate, User.class).setId(user.getId()));
        //修改成功
        if (update){
            return new Result().result200("修改成功",path);
        }
        //修改失败
        return new Result().result403("修改失败",path);
    }
}
