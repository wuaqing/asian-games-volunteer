package com.wsq.code.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/*
* 测试自定义错误的拦截器
* */

// @ControllerAdvice 拦截所有标注注有 @Controller 的控制器
@RestControllerAdvice
public class ControllerExceptionHandler {

    /*获取日志*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //ModelAndView:返回页面和后台输出到前端的一些信息
    //request:获取出现异常的访问的url
    //@ExceptionHandler:用于标识这个注解是可以做异常处理的，加上注解才有校
    //Exception.class:表示拦截的信息是Exception级别的都可以
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        //记录异常信息,在控制台输出
        logger.error("Request URL : {},Exception  : {}",request.getRequestURL(),e);

        //判断当前异常是否有状态,如果不存在则为空，存在则不为空。如果有，抛出异常让springBoot来处理；如果没有，继续运行
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }

        //返回错误页面
        ModelAndView mv = new ModelAndView();
        //添加出现异常的url
        mv.addObject("url",request.getRequestURL());
        //添加异常信息
        mv.addObject("exception",e);
        //要返回的页面：error包中的error页面
        mv.setViewName("error/error");

        return mv;
    }
}
