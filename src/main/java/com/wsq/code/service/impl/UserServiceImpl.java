package com.wsq.code.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsq.code.entity.Job;
import com.wsq.code.entity.User;
import com.wsq.code.entity.user.*;
import com.wsq.code.mapper.JobMapper;
import com.wsq.code.mapper.UserMapper;
import com.wsq.code.service.JobService;
import com.wsq.code.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsq.code.util.BeanUtil;
import com.wsq.code.util.FileUtil;
import com.wsq.code.util.MD5Utils;
import com.xiaoTools.core.regular.validation.Validation;
import com.xiaoTools.core.result.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        String girl = "女";
        String boy = "男";
        //判断性别
        if ( ! girl.equals(userRegister.getSex()) && ! boy.equals(userRegister.getSex())){
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
        return userMapper.selectByStudentId(studentId);
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
            return new Result().result403("用户未登录",path);
        }
        String girl = "女";
        String boy = "男";
        //判断性别
        if ( ! girl.equals(userUpdate.getSex()) && ! boy.equals(userUpdate.getSex())){
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

    /**
     *
     * @description: 密码修改
     * @author wsq
     * @since 2021/6/28 9:44
     * @param token:
     * @param updatePassword: 用户修改密码实体类,包含旧密码和新密码
     * @param path:
     * @return com.xiaoTools.core.result.Result
    */
    @Override
    public Result password(String token, UpdatePassword updatePassword, String path) {
        //获取该 token 对应的 user
        User user = (User) redisTemplate.opsForValue().get(token);
        //判断该用户对应的密码与用户输入的密码是否一致
        if(user.getPassword().equals(MD5Utils.code(updatePassword.getOldPassword()))){
            //密码正确,修改密码
            user.setPassword(MD5Utils.code(updatePassword.getNewPassword()));
            boolean update = this.updateById(user);
            //修改成功
            if (update){
                //更新 redis
                redisTemplate.opsForValue().set(token,user,7, TimeUnit.DAYS);
                //返回修改成功
                return new Result().result200("修改成功",path);
            }
            //修改失败
            return new Result().result403("修改失败",path);
        }
        //该用户对应的密码与用户输入的密码不一致
        return new Result().result403("密码输入错误",path);
    }

    /**
     *
     * @description: 管理员查看所有用户并分页
     * @author wsq
     * @since 2021/6/28 11:36
     * @param current: 第几页
     * @param size: 一页几条
     * @param path:
     * @return com.xiaoTools.core.result.Result
    */
    @Override
    public Result selectAllUser(Integer current, Integer size, String path) {
        //查询所有用户信息
        IPage<User> userIPage = userMapper.selectAllUser(new Page<>(current, size));
        return new Result().result200(userIPage,path);
    }

    /**
     *
     * @description: 管理员批量添加用户
     * @author wsq
     * @since 2021/6/30 16:53
     * @param file: excel 表格（包含学号、姓名、电话号码）
     * @param path:
     * @return com.xiaoTools.core.result.Result
    */
    @Override
    public Result adminAddUser(MultipartFile file, String path) {
        File transfer = null;
        try {
            transfer = FileUtil.transferToFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //导入的标题和说明的设置
        ImportParams params = new ImportParams();
        // excel 导入校验
        //导入获取集合
        List<ExcelUser> users = ExcelImportUtil.importExcel(transfer, ExcelUser.class, params);
        //将 ExcelUser to [User](entity)
        List<User> userList = BeanUtil.batchCopy(users, User.class);
        //存储正确的用户信息
        List<User> add = new ArrayList<>();
        List<User> remove = new ArrayList<>();
        int removeNum = 0;
        int addNum = 0;
        //遍历集合，将部分参数不正确的移除，并存储与remove集合中
        for (User user : userList) {
            if (userMapper.selectByStudentIdCount(user.getStudentId()) != 0) {
                remove.add(user);
                removeNum++;
                continue;
            }
            if (!Validation.isMobile(user.getTelephone())) {
                remove.add(user);
                removeNum++;
                continue;
            }
            add.add( user.setRole("user").setPassword(MD5Utils.code("123456")) );
            addNum++;
        }
        remove.forEach(System.out::println);
        System.out.println(removeNum);
        if (this.saveBatch(add)) {
            if (removeNum == 0){
                return new Result().result200("全部添加成功,密码为123456", path);
            }
            Map<String, Object> returnMassage = new HashMap<>(2);
            returnMassage.put("info","添加失败" + removeNum + "位,成功" + addNum + "位,密码为123456");
            returnMassage.put("user",remove);
            return new Result().result200(returnMassage, path);
        }
        return new Result().result403("添加失败", path);

    }

    /**
     *
     * @description: 批量添加前下载 excel 模板
     * @author wsq
     * @since 2021/7/1 16:09
     * @param response:
     * @return void
    */
    @Override
    public void adminAddUserModule(HttpServletResponse response) {
        try {
            //加载 resources 下，路径为 "static/test.xlsx" 的资源
            ClassPathResource resource = new ClassPathResource("static/test.xlsx");
            //获取文件
            File file = resource.getFile();
            //获取原文件名称
            String filename = resource.getFilename();
            //新建字节输入流
            InputStream inputStream = new FileInputStream(file);
            //强制下载不打开
            response.setContentType("application/force-download");
            //新建字节输出流
            OutputStream out = response.getOutputStream();
            //使用URLEncoder来防止文件名乱码或者读取错误
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            int b = 0;
            byte[] buffer = new byte[1000000];
            //读写
            while (b != -1) {
                //读取文件信息
                b = inputStream.read(buffer);
                if (b != -1) {
                    //写入文件信息
                    out.write(buffer, 0, b);        //当 b=-1 时，读写结束
                }
            }
            //清楚缓冲区并关闭流对象
            inputStream.close();
            out.close();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @description: 管理员删除用户
     * @author wsq
     * @since 2021/7/3 9:11
     * @param id: 学生主键
     * @param path:
     * @return com.xiaoTools.core.result.Result
    */
    @Override
    public Result adminremoveUser(String id, String path) {
        if (this.removeById(id)) {
            return new Result().result200("删除成功",path);
        }
        return new Result().result403("删除失败",path);
    }

    /**
     *
     * @description: 管理员修改用户信息
     * @author wsq
     * @since 2021/7/3 11:03
     * @param userUpdate: 管理员修改用户信息实体类
     * @param path:
     * @return com.xiaoTools.core.result.Result
    */
    @Override
    public Result adminUpdateUser(AdminUserUpdate userUpdate, String path) {
        // 在数据库中找不到该 id 的用户
        if (this.getById(userUpdate.getId()) == null) {
            return new Result().result403("请选择用户，修改失败",path);
        }
        String girl = "女";
        String boy = "男";
        //判断性别
        if ( ! girl.equals(userUpdate.getSex()) && ! boy.equals(userUpdate.getSex())){
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

        // 将 userUpdate 中的信息放入同一 id 的 User 实体类中
        User user = BeanUtil.copy(userUpdate, User.class).setId(userUpdate.getId());
        //是否重置密码
        if (userUpdate.getPassword()){
            //重置密码
            user.setPassword(MD5Utils.code("123456"));
        }
        //在数据库中根据 id 寻找 user 并修改信息
        boolean update = this.updateById(user);
        //修改成功
        if (update){
            //需要重置密码
            if (userUpdate.getPassword()){
                return new Result().result200("修改成功，密码为123456",path);
            }
            //不需要重置密码
            return new Result().result200("修改成功",path);
        }
        //修改失败
        return new Result().result403("修改失败",path);
    }

    /**
     *
     * @description: 管理员根据姓名查找用户并分页
     * @author wsq
     * @since 2021/7/3 13:38
     * @param name: 姓名
     * @param path:
     * @return com.xiaoTools.core.result.Result
    */
    @Override
    public Result adminSelectUserByName(Integer current,Integer size,String name, String path) {
        IPage<User> userIPage = userMapper.selectUserByName(new Page<>(current, size), "%" + name + "%");
        return new Result().result200(userIPage,path);
    }

}
