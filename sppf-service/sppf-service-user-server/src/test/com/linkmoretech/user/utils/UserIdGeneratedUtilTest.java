package com.linkmoretech.user.utils;

import com.linkmoretech.common.config.SnowflakeIdGenerator;
import com.linkmoretech.user.UserServerApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @Author: alec
 * @Description:
 * @date: 下午4:21 2019/4/11
 */
@Slf4j
@Component
public class UserIdGeneratedUtilTest extends UserServerApplicationTest {

    @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;

    private Set<String> id = new HashSet<>();

    @Test
    public void getNextId() {
       Random random = new Random();
       for (int i = 0; i < 200; i ++) {
           Thread thread = new Thread(new CreateId(snowflakeIdGenerator, id));
           thread.start();
       }
    }
}
@Slf4j
class CreateId implements Runnable {
    private SnowflakeIdGenerator snowflakeIdGenerator;
    private Set<String> idSet;

    public CreateId (SnowflakeIdGenerator snowflakeIdGenerator, Set<String> idSet) {
        this.snowflakeIdGenerator = snowflakeIdGenerator;
        this.idSet = idSet;
    }
    @Override
    public void run() {
        String id = String.valueOf(snowflakeIdGenerator.nextId());
        log.info("id: {}", id);
        idSet.add(id);
        log.info(" set length {}", idSet.size());
    }
}