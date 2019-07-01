package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.authentication.authentication.ValidateAuthenticationFilter;
import com.linkmoretech.auth.common.construct.ParamsConstruct;
import com.linkmoretech.auth.common.token.AppAuthenticationToken;
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
 * Description: APP 用户注册拦截过滤器
 * @date: 10:41 2019-06-28
 */
@Slf4j
public class AppRegisterAuthenticationFilter extends ValidateAuthenticationFilter {


    protected AppRegisterAuthenticationFilter() {
        super(new AntPathRequestMatcher(ParamsConstruct.REGISTER_APP, HttpMethod.POST.name()));
        log.info("个人版手机号注册过滤器 拦截 {}", ParamsConstruct.REGISTER_APP);
    }

    @Override
    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        Object[] params = this.attemptApp(request);
        AppAuthenticationToken authRequest = new AppAuthenticationToken(params[1], params[0]);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
