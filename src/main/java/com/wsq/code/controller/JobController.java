package com.wsq.code.controller;


import com.wsq.code.service.JobService;
import com.xiaoTools.core.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wsq
 * @since 2021-06-16
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Resource
    private JobService jobService;

    /**
     *
     * @description: 查询所有工作信息
     * @author wsq
     * @since 2021/6/23 15:45

     * @return com.xiaoTools.core.result.Result
    */
    @GetMapping("/selectAll")
    public Result selectAll(){
        return jobService.selectAll("/job/selectAll");
    }

}
