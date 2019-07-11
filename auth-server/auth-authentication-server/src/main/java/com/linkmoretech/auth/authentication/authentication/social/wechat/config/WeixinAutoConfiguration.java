package com.linkmoretech.auth.authentication.authentication.social.wechat.config;

import com.linkmoretech.auth.authentication.authentication.social.wechat.WeiCharConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @Author: alec
 * Description:
 * @date: 10:04 2019-07-11
 */
@Configuration
public class WeixinAutoConfiguration  extends SocialConfigurerAdapter {

    private String providerId = "weixin";

    private String clientId = "";

    private String secret = "";

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory(createConnectionFactory());
    }

    private ConnectionFactory<?> createConnectionFactory() {
        return new WeiCharConnectionFactory(providerId, clientId, secret);
    }
}
