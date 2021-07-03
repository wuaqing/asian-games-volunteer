package com.wsq.code.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 管理员修改用户信息实体类
 * </p>
 *
 * @author wsq
 * @since 2021-06-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AdminUserUpdate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 是否重置密码密码
     */
    private Boolean password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 语言
     */
    private String language;

    /**
     * 工作岗位（在1—13中选择）
     */
    private String job;

    /**
     * 年龄
     */
    private Integer age;


}
