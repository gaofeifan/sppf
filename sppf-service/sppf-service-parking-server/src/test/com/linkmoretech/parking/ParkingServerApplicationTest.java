package com.linkmoretech.parking;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: alec
 * @Description:
 * @date: 5:14 PM 2019/4/18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ParkingServerApplicationTest {
    @Test
    public void contextLoads() {
        log.info("test parking start spring boot");
    }
}