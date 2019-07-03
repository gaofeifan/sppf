package com.linkmoretech.auth.authentication.authentication.social.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.auth.authentication.authentication.social.wechat.bean.WeChatUserInfo;
import com.linkmoretech.auth.authentication.authentication.social.wechat.inteface.WeChatApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 14:14 2019-07-02
 */
public class WeCharUserInfoService extends AbstractOAuth2ApiBinding implements WeChatApi {

    private String appId;

    private String openId;

    private static final String WEIXIN_URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

    public WeCharUserInfoService (String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    @Override
    public WeChatUserInfo getWeChatUser(String openId) {

        String url = WEIXIN_URL_GET_USER_INFO + openId;

        String result = getRestTemplate().getForObject(url, String.class);

        if(StringUtils.contains(result, "errcode")) {
            return null;
        }

        WeChatUserInfo weChatUserInfo = null;

        try {
            weChatUserInfo = JSONObject.parseObject(result, WeChatUserInfo.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return weChatUserInfo;
    }

    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }
}
