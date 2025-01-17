package com.zg.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//拦截所有带controller的请求
@ControllerAdvice
public class ControllerExceptionHandler {


    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*拦截所有exception异常*/
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        //日志记录错误信息
        logger.error("Request Url : {}, Exception : {}",request,e.getMessage());

        /*将找不到资源异常交给springboot来处理，ControllerAdvice不拦截*/
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!= null) {
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url:", request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}
