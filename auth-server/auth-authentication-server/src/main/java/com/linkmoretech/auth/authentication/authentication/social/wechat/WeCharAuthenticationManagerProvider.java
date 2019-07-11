package com.linkmoretech.auth.authentication.authentication.social.wechat;

import com.linkmoretech.auth.authentication.authentication.social.wechat.inteface.WeChatApi;
import com.linkmoretech.auth.authentication.authentication.social.wechat.service.WeCharUserInfoService;
import com.linkmoretech.auth.authentication.authentication.social.wechat.template.WeixinOAuth2Template;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @Author: alec
 * Description:
 * @date: 09:55 2019-07-11
 */
/**
 * 微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 */
public class WeCharAuthenticationManagerProvider extends AbstractOAuth2ServiceProvider<WeChatApi> {

    /**
     * 微信获取授权码的url
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public WeCharAuthenticationManagerProvider(String appId, String appSecret) {
        super(new WeixinOAuth2Template(appId, appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
    }

    @Override
    public WeChatApi getApi(String accessToken) {
        return new WeCharUserInfoService(accessToken);
    }
}
