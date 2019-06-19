package com.linkmoretech.auth.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Author: alec
 * Description: JWTToken配置
 * @date: 13:01 2019-06-14
 */
@Configuration
public class JwtTokenStoreConfig {

    //@Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

   // @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter () {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey("linkmoretech");
        return tokenConverter;
    }

    //@Bean
    public TokenEnhancer jwtTokenEnchcer() {
        return new JwtTokenEnchcer();
    }

}
