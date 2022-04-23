package com.atguigu.servicebase.exceptionhander;

import com.atguigu.commonutils.RestCode;
import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.util.ExceptionUtil;
import com.atguigu.servicebase.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Niebelungen
 * @create: 2022/4/5-11:04
 * @VERSION: 1.0
 */
@ControllerAdvice
@Slf4j
public class HanderException {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理");
    }
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("执行了特定的异常处理");
    }
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result error(MyException e){
        e.printStackTrace();
        log.error(e.getMsg());//记录日志
//        log.error(ExceptionUtil.getMessage(e));
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
}
