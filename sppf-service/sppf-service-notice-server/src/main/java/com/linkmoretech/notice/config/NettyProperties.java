package com.linkmoretech.notice.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/9
 */
@Configuration
@Data
public class NettyProperties {

    private int port = 9000;

    private String hort = "192.168.1.241";

    private int bossCount;

    private int workCount;

    private boolean allowCustomRequests;

    private int upgradeTimeout;

    private int pingTimeout;

    private int pingInterval;


}
