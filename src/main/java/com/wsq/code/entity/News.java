package com.wsq.code.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wsq
 * @since 2021-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("table_news")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻内容
     */
    private String content;

    /**
     * 新闻首图（链接）
     */
    @TableField(value = "first_picture")
    private String firstPicture;

    /**
     * 新闻标记（原创/翻译/转载）
     */
    private String flag;

    /**
     * 浏览次数（默认为0）
     */
    private String views;

    /**
     * 是否发布
     */
    @TableField(value = "is_publish")
    private String isPublish;

    /**
     * 创建时间
     */
    @TableField(value = "creation_time")
    private LocalDateTime creationTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;


}
