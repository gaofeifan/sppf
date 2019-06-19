package com.linkmoretech.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * @Description:
 * @date: 下午4:34 2019/4/16
 */
@ConfigurationProperties(prefix = "generate")
@Component
@Data
public class GenerateConfig {

    private Integer workId;

    private Integer dataId;

}
