package com.linkmoretech.auth.authentication.authentication.weixin;

import com.linkmoretech.auth.authentication.authentication.MultiReadHttpServletRequest;
import com.linkmoretech.auth.authentication.authentication.ValidateFailureHandler;
import com.linkmoretech.auth.authentication.authentication.sms.mobile.ValidateCodeException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: alec
 * Description:
 * @date: 10:59 2019-07-11
 */
public class WexinCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private String filterUrl = "weixin/auth";

    private ValidateFailureHandler validateFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        if (request.getRequestURI().equals(filterUrl)) {
            try {
                MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(request);
                /**
                 * 验证token是否合法
                 * */

                chain.doFilter(multiReadHttpServletRequest, response);
            } catch (ValidateCodeException e) {
                validateFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }

        } else {
            chain.doFilter(request, response);
        }

    }


    private void getOpenId( MultiReadHttpServletRequest multiReadHttpServletRequest) throws  ValidateCodeException {

    }

}
