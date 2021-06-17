package com.wsq.code.entity;

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
 * 考试内容实体类
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
@TableName("table_exam_content")
public class ExamContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 标题（试卷标题）
     */
    private String title;

    /**
     * 题号（第x题）
     */
    private Integer number;

    /**
     * 题目
     */
    private String questions;

    /**
     * 选项A
     */
    @TableField(value = "option_a")
    private String optionA;

    /**
     * 选项B
     */
    @TableField(value = "option_b")
    private String optionB;

    /**
     * 选项C
     */
    @TableField(value = "option_c")
    private String optionC;

    /**
     * 选项D
     */
    @TableField(value = "option_d")
    private String optionD;

    /**
     * 答案
     */
    private String answer;


}
