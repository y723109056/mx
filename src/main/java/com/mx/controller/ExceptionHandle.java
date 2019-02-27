package com.mx.controller;

import com.mx.exception.Result;
import com.mx.exception.ResultUtil;
import com.mx.exception.UnKnowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 小米线儿
 * @time 2019/2/13 0013
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
@ControllerAdvice
public class ExceptionHandle {


    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionGet(Exception e){
        if(e instanceof UnKnowException){
            UnKnowException ue = (UnKnowException) e;
            return ResultUtil.fail(ue.getCode(), ue.getMessage());
        }
//        LOGGER.error("系统异常:",e);
        return ResultUtil.fail(-1,e.getMessage());
    }





}
