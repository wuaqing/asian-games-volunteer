package com.wsq.code.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsq.code.entity.Job;
import com.wsq.code.entity.News;
import com.wsq.code.mapper.JobMapper;
import com.wsq.code.mapper.NewsMapper;
import com.wsq.code.service.JobService;
import com.wsq.code.service.NewsService;
import com.xiaoTools.core.result.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    JobMapper jobMapper;

    /**
     *
     * @description: 查询所有工作信息
     * @author wsq
     * @since 2021/6/23 15:47
     * @param path: 路径
     * @return com.xiaoTools.core.result.Result
    */
    @Override
    public Result selectAll(String path) {
        //查询全部工作信息
        List<Job> list = this.list();
        //查询成功
        return new Result().result200(list,path);
    }
}
