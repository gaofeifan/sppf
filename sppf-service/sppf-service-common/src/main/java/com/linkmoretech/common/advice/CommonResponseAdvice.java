package com.linkmoretech.common.advice;

import com.linkmoretech.common.annation.IgnoreResponseAdvice;
import com.linkmoretech.common.util.ResponseUtil;
import com.linkmoretech.common.vo.ResponseCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.constraints.NotNull;

/**
 * 统一响应码
 * @Author: alec
 * @Description:
 * @date: 上午10:59 2019/4/10
 */
@RestControllerAdvice
@Slf4j
public class CommonResponseAdvice implements ResponseBodyAdvice<Object> {
    private final String IGNORE_CLASS = "springfox.documentation.swagger.web.ApiResourceController";
    private final String IGNORE_WEB = "springfox.documentation.swagger2.web.Swagger2Controller";

    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        /**
         * 对swagger 响应不拦截
         * */
        String className = methodParameter.getDeclaringClass().getName();
        if (IGNORE_CLASS.equals(className) || IGNORE_WEB.equals(className)) {
            return false;
        }
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        return true;
    }

    @Override
    @NotNull
    @SuppressWarnings("all")
    public Object beforeBodyWrite(@NotNull Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info("统一拦截响应格式.. {} - {}" , methodParameter.getDeclaringClass().getName(), methodParameter.getMethod().getName());
        if (o == null) {
            return ResponseUtil.returnSuccess();
        } else if (o instanceof ResponseCommon) {
            return (ResponseCommon)o;
        } else {
            return ResponseUtil.returnSuccess(o);
        }
    }
}
