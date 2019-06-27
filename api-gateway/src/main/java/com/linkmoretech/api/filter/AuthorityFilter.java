package com.linkmoretech.api.filter;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
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

    private final String ACCESS_TOKEN = "accessToken";

    private final String AUTHORIZE_TOKEN = "Authorization";

    private final String secretKey = "123456";

    private final String clientId = "linkmoretech";

    private final String clientSecurt = "linkmore2018";

    String[] skipAuthUrls = {"/account/system/login", "/account/login-mobile",
            "/account/personal/login", "/account/personal/login-mobile", "/account/sms/code"};


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
        String token =  clientId + ":" + clientSecurt;
        token = "Basic " +  Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));
        requestContext.addZuulRequestHeader(ACCESS_TOKEN, token);
        //跳过不需要验证的路径
        if (Arrays.asList(skipAuthUrls).contains(url)) {
            log.info("传递token {}", token);
            requestContext.getZuulRequestHeaders().put(AUTHORIZE_TOKEN, "*");
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
    public Object run() throws ZuulException {
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
        /*


        String userName = validateJwt(token);
        log.info("userName is {}", userName);
        if (StringUtils.isEmpty(userName)) {
            // 过滤该请求，不对其进行路由
            currentContext.setSendZuulResponse(false);
            //返回错误代码
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        String method = request.getMethod();
        log.info("method is {}", method);
        if (HttpMethod.POST.name().equals(method)) {
            *//**
             * 追加参数
             * *//*
            try {
                InputStream in = currentContext.getRequest().getInputStream();
                String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
                log.info("body {}", body);
                // 如果body为空初始化为空json
                if (StringUtils.isBlank(body)) {
                    body = "{}";
                }
                JSONObject jsonObject = JSONObject.parseObject(body);
                jsonObject.put(NAME_KEY, userName);
                String newBody = jsonObject.toString();
                final byte[] reqBodyBytes = newBody.getBytes();
                // 重写上下文的HttpServletRequestWrapper
                currentContext.setRequest(new HttpServletRequestWrapper(request) {
                    @Override
                    public ServletInputStream getInputStream() throws IOException {
                        return new ServletInputStreamWrapper(reqBodyBytes);
                    }
                    @Override
                    public int getContentLength() {
                        return reqBodyBytes.length;
                    }
                    @Override
                    public long getContentLengthLong() {
                        return reqBodyBytes.length;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            request.getParameterMap();
            Map<String, List<String>> requestQueryParams = currentContext.getRequestQueryParams();
            if (requestQueryParams == null) {
                requestQueryParams = new HashMap<>();
            }
            List<String> arrayList = new ArrayList<>();
            arrayList.add(userName);
            requestQueryParams.put(NAME_KEY, arrayList);
            currentContext.setRequestQueryParams(requestQueryParams);
        }*/

        return null;
    }


    private String validateJwt(String token) {
        String userName = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("MING")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            userName = jwt.getClaim("userName").asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
    }
}
