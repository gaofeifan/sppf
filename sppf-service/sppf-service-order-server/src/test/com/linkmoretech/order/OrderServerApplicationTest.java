package com.linkmoretech.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: alec
 * @Description:
 * @date: 下午1:53 2019/4/15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServerApplicationTest {
    @Test
    public void contextLoads() {
        log.info("test application");
    }
}