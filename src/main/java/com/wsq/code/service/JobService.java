package com.wsq.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsq.code.entity.Job;
import com.wsq.code.entity.News;
import com.xiaoTools.core.result.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wsq
 * @since 2021-06-16
 */
public interface JobService extends IService<Job> {

    /**
     *
     * @description: 查询所有工作信息
     * @author wsq
     * @since 2021/6/23 15:47
     * @param path: 路径
     * @return com.xiaoTools.core.result.Result
    */
    Result selectAll(String path);
}
