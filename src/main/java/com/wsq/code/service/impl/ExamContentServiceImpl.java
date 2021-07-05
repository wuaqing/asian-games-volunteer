package com.wsq.code.service.impl;

import com.wsq.code.entity.ExamContent;
import com.wsq.code.mapper.ExamContentMapper;
import com.wsq.code.service.ExamContentService;
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
public class ExamContentServiceImpl extends ServiceImpl<ExamContentMapper, ExamContent> implements ExamContentService {

    /**
     *
     * @description: 管理员发布考试,保存考试内容
     * @author wsq
     * @since 2021/7/5 9:38
     * @param examContent: 考试内容
     * @return java.lang.Boolean
    */
    @Override
    public Boolean saveExam(ExamContent examContent) {
        return this.save(examContent);
    }
}
