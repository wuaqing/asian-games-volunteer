package com.wsq.code.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户登录实体类
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
public class UserLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学号（唯一不重复）
     */
    private String studentId;

    /**
     * 密码
     */
    private String password;
}
