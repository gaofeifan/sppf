package com.linkmoretech.auth.authentication.authentication.social.wechat;

import com.linkmoretech.auth.authentication.authentication.social.wechat.adapter.WeixinAdapter;
import com.linkmoretech.auth.authentication.authentication.social.wechat.bean.WeChatAccessGrant;
import com.linkmoretech.auth.authentication.authentication.social.wechat.inteface.WeChatApi;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @Author: alec
 * Description:
 * @date: 09:58 2019-07-11
 */
public class WeiCharConnectionFactory extends OAuth2ConnectionFactory<WeChatApi> {


    public WeiCharConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WeCharAuthenticationManagerProvider(appId, appSecret), new WeixinAdapter());
    }

    /**
     * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     * @param accessGrant
     * @return
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WeChatAccessGrant) {
            return ((WeChatAccessGrant)accessGrant).getOpenId();
        }
        return null;
    }


    /**
     *
     * @param accessGrant
     * @return
     */
    public Connection<WeChatApi> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<WeChatApi>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    /**
     *
     * @param data
     * @return
     */
    public Connection<WeChatApi> createConnection(ConnectionData data) {
        return new OAuth2Connection<WeChatApi>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    /**
     *
     * @param providerUserId
     * @return
     */
    private ApiAdapter<WeChatApi> getApiAdapter(String providerUserId) {
        return new WeixinAdapter(providerUserId);
    }

    /**
     *
     * @return
     */
    private OAuth2ServiceProvider<WeChatApi> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WeChatApi>) getServiceProvider();
    }
}
