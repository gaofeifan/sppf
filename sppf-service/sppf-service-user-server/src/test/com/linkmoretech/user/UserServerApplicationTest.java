package com.linkmoretech.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: alec
 * @Description:
 * @date: 下午5:01 2019/4/11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserServerApplicationTest {
    @Test
    public void contextLoads() {
      log.info("test application");
    }

}