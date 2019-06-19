package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.authentication.authentication.ValidateAuthenticationFilter;
import com.linkmoretech.auth.authentication.construct.ParamsConstruct;
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
public class SmsAuthenticationPersonalFilter extends ValidateAuthenticationFilter {

    private String mobileParams = "mobile";

    protected SmsAuthenticationPersonalFilter() {
        super(new AntPathRequestMatcher(ParamsConstruct.LOGIN_MOBILE_PERSONAL, HttpMethod.POST.name()));
        log.info("个人版短信登录过滤器", ParamsConstruct.LOGIN_MOBILE_PERSONAL);
    }

    @Override
    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        /*if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        Map<String, String> loginParams = getLoginParams(request);
        String clientId = loginParams.get(this.CLIENT_ID);
        String mobile = loginParams.get(mobileParams);*/
        String[] params = this.attempt(request);
        SmsAuthenticationPersonalToken authRequest = new SmsAuthenticationPersonalToken(params[1], params[0]);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

   /* private String mobileKey = "personal";

    private boolean postOnly = true;

    public SmsAuthenticationPersonalFilter() {
        super(new AntPathRequestMatcher(ParamsConstruct.LOGIN_MOBILE_PERSONAL, HttpMethod.POST.name()));
        log.info("过滤器2 拦截 {}", ParamsConstruct.LOGIN_MOBILE_PERSONAL);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            Map loginParams = getLoginParams(request);
            String username = (String) loginParams.get(mobileKey);
            if (username == null) {
                username = "";
            }
            *//**
             * 认证流程验证码是否正确
             * *//*
            username = username.trim();
            SmsAuthenticationPersonalToken authRequest = new SmsAuthenticationPersonalToken(username, "a");
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void setDetails(HttpServletRequest request, SmsAuthenticationPersonalToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    protected Map<String, String> getLoginParams(HttpServletRequest request) {
        Map returnMap = new HashMap<>();
        try {
            BufferedReader br = request.getReader();
            String str, wholeStr = "";
            while ((str = br.readLine()) != null) {
                wholeStr += str;
            }
            log.info(wholeStr);

            returnMap = JSONObject.parseObject(wholeStr, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }*/
}
