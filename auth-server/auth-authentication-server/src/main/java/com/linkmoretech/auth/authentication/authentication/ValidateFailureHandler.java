package com.linkmoretech.auth.authentication.authentication;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: alec
 * Description:
 * @date: 10:31 2019-06-13
 */
@Component
@Slf4j
public class ValidateFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.warn("登录失败 {}", e.getMessage());
        String message = e.getMessage();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setCode(HttpStatus.UNAUTHORIZED.value());
        loginResponse.setMessage(message);
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSONObject.toJSONString(loginResponse));
    }
}
