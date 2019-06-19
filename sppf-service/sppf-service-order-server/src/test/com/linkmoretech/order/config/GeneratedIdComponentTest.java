package com.linkmoretech.order.config;

import com.linkmoretech.order.OrderServerApplicationTest;
import com.linkmoretech.order.enums.OrderTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;

/**
 * @Author: alec
 * @Description:
 * @date: 下午1:51 2019/4/15
 */
@Component
@Slf4j
public class GeneratedIdComponentTest extends OrderServerApplicationTest {

    @Autowired
    GeneratedIdComponent generatedIdComponent;

    @Test
    public void getOrderId() {
        String userId = "2122335431212432";
        for (int i= 0; i<20; i++) {
            String orderId = generatedIdComponent.getOrderId(userId, OrderTypeEnum.SUBSCRIBE.getCode());
            log.info("order is {} ", orderId);
        }
    }
}