package com.linkmoretech.auth.authentication.authentication.sms.manager;

import com.linkmoretech.auth.authentication.authentication.ValidateAuthenticationFilter;
import com.linkmoretech.auth.common.construct.ParamsConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: alec
 * Description: 管理版短信登录过滤器
 * @date: 15:28 2019-06-17
 */
@Slf4j
public class SmsAuthenticationManagerFilter extends ValidateAuthenticationFilter {



    public SmsAuthenticationManagerFilter() {
        super(new AntPathRequestMatcher(ParamsConstruct.LOGIN_MANAGE_MOBILE, HttpMethod.POST.name()));
        log.info("管理版手机号登录过滤器 拦截 {}", ParamsConstruct.LOGIN_MANAGE_MOBILE);
    }

    @Override
    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String[] params = this.attempt(request);
        SmsAuthenticationManagerToken authRequest = new SmsAuthenticationManagerToken(params[1], params[0]);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
