package com.wsq.code.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsq.code.entity.Job;
import com.wsq.code.entity.News;
import com.wsq.code.mapper.JobMapper;
import com.wsq.code.mapper.NewsMapper;
import com.wsq.code.service.JobService;
import com.wsq.code.service.NewsService;
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
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

}
