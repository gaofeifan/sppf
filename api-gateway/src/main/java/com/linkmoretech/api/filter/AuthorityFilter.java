package com.linkmoretech.api.filter;

import com.linkmoretech.api.configuration.AuthConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Author: alec
 * Description: 权限拦截器
 * 后续结合oauth2
 * @date: 17:13 2019-06-05
 */
@Component
@Slf4j
public class AuthorityFilter extends ZuulFilter {

    private final String TYPE_PRE = "pre";

    private final String AUTHORIZE_TOKEN = "Authorization";

    private final String clientId = "linkmoretech";

    private final String clientSecurt = "linkmore2018";


    @Autowired
    AuthConfig authConfig;

   /* String[] skipAuthUrls = {"/account/system/login", "/account/login-mobile",
            "/account/personal/login", "/account/personal/login-mobile", "/account/sms/code",
    "/swagger-ui.html",
	"/configuration",
	"/swagger-resources",
	"/swagger-resources/configuration/ui",
	"/swagger-ui.html/swagger-resources/configuration/ui",
	"/swagger-resources/configuration/security",
	"/api-docs",
	"/v2/api-docs",
    
    };*/


    /**
     * 返回一个字符串代表过滤器的类型
     * pre表示被路由之前
     */
    @Override
    public String filterType() {
        return TYPE_PRE;
    }

    /**
     * 过滤顺序
     * */
    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //共享RequestContext，上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String url = request.getRequestURI();
        String token =  authConfig.getClientId() + ":" + authConfig.getClientSecure();
        log.info("token {}", token);
        token = "Basic " +  Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));
        String ACCESS_TOKEN = "accessToken";
        requestContext.addZuulRequestHeader(ACCESS_TOKEN, token);
        //跳过不需要验证的路径
        log.info("不需要认证URL {}", authConfig.getIgnores());
        if (Arrays.asList(authConfig.getIgnores()).contains(url)) {
            log.info("传递token {}", token);
            requestContext.addZuulRequestHeader(AUTHORIZE_TOKEN, "*");
            return false;
        }
        return true;
    }

    /**
     * 过滤器执行过滤逻辑
     *
     * 如果是放行服务，则直接通过
     *
     * 如果是非放行服务
     *
     * 获取请求，从请求头部取得token,弱无Token 则返回请求失败
     *
     * 根据Token 从认证服务器校验请求是否合法，如果合法放行通过。
     *
     * 用Token 换取 jwt 调用到具体服务
     *
     * 具体服务拿到jwt 解析 jwt 中权限信息 及数据信息
     *
     * */
    @Override
    public Object run() {
        log.info("run");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = request.getHeader(AUTHORIZE_TOKEN);
        if (StringUtils.isEmpty(token)) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            currentContext.setResponseBody("Authorization token is empty");
        }
        log.info(token);
        return null;
    }
}
