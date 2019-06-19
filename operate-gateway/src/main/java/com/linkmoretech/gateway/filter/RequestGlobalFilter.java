package com.linkmoretech.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: alec
 * Description: 全局过滤器 限流 黑名单过滤
 * @date: 11:58 2019-05-14
 */
@Component
@Slf4j
public class RequestGlobalFilter implements GlobalFilter, Ordered {
    private final String AUTHORIZE_TOKEN = "Authorization";

    private final String secretKey = "123456";

    private final String NAME_KEY = "username";

    String[] skipAuthUrls = {"/account/login"};

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest= exchange.getRequest();
        String url = serverHttpRequest.getPath().value();
        //跳过不需要验证的路径
        log.info("url {}", url);
        if(Arrays.asList(skipAuthUrls).contains(url)){
            return chain.filter(exchange);
        }
        //从请求头中取出token
        String token = serverHttpRequest.getHeaders().getFirst(AUTHORIZE_TOKEN);
        ServerHttpResponse response = exchange.getResponse();
        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        String userName = validateJwt(token);
        if (StringUtils.isEmpty(userName)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        /*
        String method = serverHttpRequest.getMethodValue();
        //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
        URI uri = serverHttpRequest.getURI();
        ServerHttpRequest newRequest;
        if (HttpMethod.POST.name().equals(method)) {
            String bodyStr = resolveBodyFromRequest(serverHttpRequest);
            //TODO 得到Post请求的请求参数后，做你想做的事
            log.info(bodyStr);
        }
        if (HttpMethod.POST.name().equals(method)) {
            //从请求里获取Post请求体
            String bodyStr = resolveBodyFromRequest(serverHttpRequest);
            //TODO 得到Post请求的请求参数后，做你想做的事
            log.info(bodyStr);
            JSONObject jsonObject = JSONObject.parseObject(bodyStr);
            jsonObject.put(NAME_KEY, userName);
            bodyStr = jsonObject.toJSONString();
            log.info(bodyStr);
            DataBuffer dataBuffer = stringBuffer(bodyStr);
            Flux<DataBuffer> bodyFlux = Flux.just(dataBuffer);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            int length = bodyStr.getBytes().length;
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            headers.setContentLength(length);
            ServerHttpRequest request = serverHttpRequest.mutate().uri(uri).build();
            newRequest = new ServerHttpRequestDecorator(request){
                @Override
                public HttpHeaders getHeaders() {
                    long contentLength = headers.getContentLength();
                    HttpHeaders requestHeard = new HttpHeaders();
                    requestHeard.putAll(super.getHeaders());
                    if (contentLength > 0) {
                        requestHeard.setContentLength(contentLength);
                    } else {
                        requestHeard.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                    }
                    return requestHeard;
                }
                @Override
                public Flux<DataBuffer> getBody() {
                    return bodyFlux;
                }
            };
            newRequest.mutate().header(HttpHeaders.CONTENT_LENGTH, Integer.toString(bodyStr.length()));
        } else  {
            URI newUri = setNoRequestBodyParams(uri, userName);
            newRequest = exchange.getRequest().mutate().uri(newUri).build();
        }
        return chain.filter(exchange.mutate().request(newRequest).build());*/
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
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

    /**
     * 从Flux<DataBuffer>中获取字符串的方法
     * @return 请求体
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        StringBuilder sb = new StringBuilder();

        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });
        return sb.toString();
    }

    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    private URI setNoRequestBodyParams(URI uri, String username) {
        String value = uri.getRawQuery();
        StringBuilder query = new StringBuilder();
        String compareTo = "=";
        String linkValue = "&";
        query.append(NAME_KEY).append(compareTo).append(username);
        if (!StringUtils.isEmpty(value)) {
            query.append(linkValue).append(value);
        }
        URI newUri = UriComponentsBuilder.fromUri(uri)
                .replaceQuery(query.toString())
                .build(true)
                .toUri();
        return newUri;
    }

}
