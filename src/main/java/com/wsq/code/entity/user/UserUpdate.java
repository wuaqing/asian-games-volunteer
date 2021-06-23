package com.wsq.code.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户修改信息实体类
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
public class UserUpdate implements Serializable {

    private static final long serialVersionUID = 1L;

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
