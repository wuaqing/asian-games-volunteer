package com.wsq.code.service;

import com.wsq.code.entity.ExamContent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wsq
 * @since 2021-06-16
 */
public interface ExamContentService extends IService<ExamContent> {

    /**
     *
     * @description: 管理员发布考试,保存考试内容
     * @author wsq
     * @since 2021/7/5 9:37
     * @param examContent: 考试内容
     * @return java.lang.Boolean
    */
    Boolean saveExam (ExamContent examContent);

}
