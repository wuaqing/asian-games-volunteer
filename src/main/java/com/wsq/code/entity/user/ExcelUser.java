package com.wsq.code.entity.user;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *
 * @description: 批量添加用户
 * @author wsq
 * @since 2021/6/30 9:48
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ExcelTarget("users")
public class ExcelUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 学号
     */
    @Excel(name = "学号")
    private String studentId;

    /**
     * 电话号码
     */
    @Excel(name = "电话号码")
    private String telephone;
}
