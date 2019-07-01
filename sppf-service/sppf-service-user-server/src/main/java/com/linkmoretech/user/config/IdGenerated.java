package com.linkmoretech.user.config;

import com.linkmoretech.common.config.SnowflakeIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: alec
 * @Description:
 * @date: 下午4:33 2019/4/16
 */
@Configuration
@Slf4j
public class IdGenerated {

    @Autowired
    GenerateConfig generateConfig;

    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        log.info("workID : {} , dataId : {}", generateConfig.getWorkId(), generateConfig.getDataId());
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(generateConfig.getWorkId(), generateConfig.getDataId());
        return snowflakeIdGenerator;
    }
}
