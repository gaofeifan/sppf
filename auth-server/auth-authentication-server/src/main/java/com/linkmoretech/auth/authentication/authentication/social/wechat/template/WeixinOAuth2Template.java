package com.linkmoretech.auth.authentication.authentication.social.wechat.template;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.auth.authentication.authentication.social.wechat.bean.WeChatAccessGrant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @Author: alec
 * Description: 微信认证模版类
 * @date: 09:42 2019-07-11
 */
@Slf4j
public class WeixinOAuth2Template extends OAuth2Template {

    private String clientId;

    private String clientSecret;

    private String accessTokenUrl;

    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    public WeixinOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);  //请求中添加client_id和client_secret参数
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    /**
     * 微信变更了OAuth请求参数的名称，我们覆写相应的方法按照微信的文档改为微信请求参数的名字。
     * @param authorizationCode
     * @param redirectUri
     * @param parameters
     * @return
     */
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> parameters) {
        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);

        accessTokenRequestUrl.append("?appid="+clientId);
        accessTokenRequestUrl.append("&secret="+clientSecret);
        accessTokenRequestUrl.append("&code="+authorizationCode);
        accessTokenRequestUrl.append("&grant_type=authorization_code");
        accessTokenRequestUrl.append("&redirect_uri="+redirectUri);
        return getAccessToken(accessTokenRequestUrl);
    }




    /**
     * 微信变更了OAuth请求参数的名称，我们覆写相应的方法按照微信的文档改为微信请求参数的名字。
     * @param refreshToken
     * @param additionalParameters
     * @return
     */
    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {

        StringBuilder refreshTokenUrl = new StringBuilder(REFRESH_TOKEN_URL);

        refreshTokenUrl.append("?appid="+clientId);
        refreshTokenUrl.append("&grant_type=refresh_token");
        refreshTokenUrl.append("&refresh_token="+refreshToken);

        return getAccessToken(refreshTokenUrl);
    }

    /**
     * 获取微信access_token信息
     * @param accessTokenRequestUrl
     * @return
     */
    @SuppressWarnings("unchecked")
    private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl) {

        log.info("获取access_token, 请求URL: "+accessTokenRequestUrl.toString());

        String response = getRestTemplate().getForObject(accessTokenRequestUrl.toString(), String.class);

        log.info("获取access_token, 响应内容: "+response);

        Map<String, Object> result = null;
        try {
            result = JSONObject.parseObject(response, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回错误码时直接返回空
        if(StringUtils.isNotBlank(MapUtils.getString(result, "errcode"))){
            String errcode = MapUtils.getString(result, "errcode");
            String errmsg = MapUtils.getString(result, "errmsg");
            throw new RuntimeException("获取access token失败, errcode:"+errcode+", errmsg:"+errmsg);
        }

        WeChatAccessGrant accessToken = new WeChatAccessGrant(
                MapUtils.getString(result, "access_token"),
                MapUtils.getString(result, "scope"),
                MapUtils.getString(result, "refresh_token"),
                MapUtils.getLong(result, "expires_in"));

        accessToken.setOpenId(MapUtils.getString(result, "openid"));

        return accessToken;
    }


    /**
     * 构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     */
    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid="+clientId+"&scope=snsapi_login";
        return url;
    }
    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
       return buildAuthenticateUrl(parameters);
    }

    /**
     * 微信返回的contentType是html/text，添加相应的HttpMessageConverter来处理。
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
