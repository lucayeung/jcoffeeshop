package com.luca.jcoffeeshop.error;

import com.luca.jcoffeeshop.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author hujinbo
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义业务异常、参数异常
     */
    @ExceptionHandler({
            BizException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult bizExceptionHandler(HttpServletRequest request, Exception ex) {
        log.error("访问路径:{}，业务异常：{}", request.getServletPath(), ex.getMessage(), ex);
        return new ResponseResult(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), getCauseMessage(ex));
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult otherExceptionHandler(HttpServletRequest request, Exception ex) {
        log.error("访问路径:{}，未知异常：{}", request.getServletPath(), ex.getMessage(), ex);
        return new ResponseResult(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), getCauseMessage(ex));
    }

    /**
     * 获取内部异常错误信息
     */
    private static String getCauseMessage(Throwable ex) {
        if (ex == null) {
            return null;
        }

        Throwable cause = ex.getCause();
        return cause == null ? null : cause.getMessage();
    }
}
