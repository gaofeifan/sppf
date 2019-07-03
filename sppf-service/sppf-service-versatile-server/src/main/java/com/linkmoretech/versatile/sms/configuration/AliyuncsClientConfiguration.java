package com.linkmoretech.versatile.sms.configuration;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.linkmoretech.versatile.sms.config.AliyuncsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: alec
 * Description:
 * @date: 14:39 2019-06-21
 */
@Configuration
@Slf4j
public class AliyuncsClientConfiguration {

    private final String CONNECT_TIMOUT = "sun.net.client.defaultConnectTimeout";

    private final String READ_TIME = "sun.net.client.defaultReadTimeout";

    @Autowired
    AliyuncsConfig aliyuncsConfig;

    @Bean
    public IAcsClient iAcsClient () throws ClientException {

        System.setProperty(CONNECT_TIMOUT, aliyuncsConfig.getDefaultConnectTimeout());
        System.setProperty(READ_TIME, aliyuncsConfig.getDefaultReadTimeout());
        log.info("初始化阿里云短信发送客户端 {}", aliyuncsConfig);
        IAcsClient iAcsClient = new DefaultAcsClient(clientProfile());
        return iAcsClient;
    }

    @Bean
    public IClientProfile clientProfile() throws ClientException {

        IClientProfile iClientProfile = DefaultProfile.getProfile(aliyuncsConfig.getArea(),
                aliyuncsConfig.getAccessKeyId(),
                aliyuncsConfig.getAccessKeySecret());

        DefaultProfile.addEndpoint(aliyuncsConfig.getArea(), aliyuncsConfig.getArea(),
                aliyuncsConfig.getProduct(), aliyuncsConfig.getSendUrl());

        return iClientProfile;
    }

    @Bean
    public SendSmsRequest sendSmsRequest () {

        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setSignName(aliyuncsConfig.getSign());
        return sendSmsRequest;
    }
}
