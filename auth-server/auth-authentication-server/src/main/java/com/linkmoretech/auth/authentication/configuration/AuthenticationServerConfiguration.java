package com.linkmoretech.auth.authentication.configuration;

import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @Author: alec
 * Description: 授权认证服务器，处理Token的生成和存储，验证
 * @date: 15:09 2019-06-13
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
public class AuthenticationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTokenStore redisTokenStore;

    @Autowired
    UserDetailAccountAbstract userDetailAccountAbstract;

    @Autowired
    AuthenticationClientConfig authenticationClientConfig;

    /**
     * 认证及token配置
     */

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)  {
        log.info("token 认证");
        endpoints.tokenStore(redisTokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailAccountAbstract);
    }

    /**
     * tokenKey的访问权限表达式配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()");
    }

    /**
     * 配置Oauth2 客户端认证
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        /**
         * 配置管理版认证密钥
         * */
       /* builder.withClient("linkmoretech")
                .secret("linkmore2018")
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                .accessTokenValiditySeconds(10000)
                .refreshTokenValiditySeconds(10000)
                .scopes("all");*/
       log.info("初始化配置oauth2 认证密钥 {}", authenticationClientConfig);
       builder.withClient(authenticationClientConfig.getManageClient())
              .secret(authenticationClientConfig.getManageSecret())
              .authorizedGrantTypes("refresh_token", "authorization_code", "password")
              .accessTokenValiditySeconds(10000)
              .refreshTokenValiditySeconds(10000)
              .scopes("all")

              .and()
              .withClient(authenticationClientConfig.getPlatformClient())
              .secret(authenticationClientConfig.getPlatformSecret())
              .accessTokenValiditySeconds(10000)
              .refreshTokenValiditySeconds(10000)
              .scopes("all")

              .and()
              .withClient(authenticationClientConfig.getSystemClient())
              .secret(authenticationClientConfig.getSystemSecret())
              .accessTokenValiditySeconds(10000)
              .refreshTokenValiditySeconds(10000)
              .scopes("all")

              .and()
              .withClient(authenticationClientConfig.getPersonalClient())
              .secret(authenticationClientConfig.getPersonalSecret())
              .accessTokenValiditySeconds(10000)
              .refreshTokenValiditySeconds(10000)
              .scopes("all");
    }
}
