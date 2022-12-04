package top.mnsx.handler.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.mnsx.domain.ResponseResult;
import top.mnsx.enums.AppHttpCodeEnum;
import top.mnsx.exception.SystemException;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult sysExceptionHandler(SystemException e) {
        // 打印异常信息
        log.error("异常被抛出——", e);
        // 从异常中获取信息封装返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        // 打印异常信息
        log.error("异常被抛出——", e);
        // 从异常中获取信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
