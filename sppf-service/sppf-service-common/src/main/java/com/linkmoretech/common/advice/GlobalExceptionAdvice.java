package com.linkmoretech.common.advice;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.util.ResponseUtil;
import com.linkmoretech.common.vo.ResponseCommon;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

/**
 * @Author: alec
 * @Description:
 * @date: 上午11:41 2019/4/10
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @SuppressWarnings("unchecked")
    @ExceptionHandler(value = CommonException.class)
    public ResponseCommon<String> handleGlobalException(HttpServletRequest request, CommonException commonException) {
        return ResponseUtil.returnFailure(commonException);
    }

    /**
     * 拦截参数缺失异常
     * */
    @SuppressWarnings("unchecked")
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseCommon<String> handleMissingRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException exception){
        exception.printStackTrace();
        return ResponseUtil.returnFailure(ResponseCodeEnum.PARAMS_ERROR);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public  ResponseCommon<String> AccessDeniedException (AccessDeniedException accessDeniedException) {
        return ResponseUtil.returnFailure(ResponseCodeEnum.UNAUTHORIZED, accessDeniedException.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseCommon<String> otherException(Exception exception){
        exception.printStackTrace();
        return ResponseUtil.returnFailure(ResponseCodeEnum.FAILURE, exception.getMessage());
    }
}
