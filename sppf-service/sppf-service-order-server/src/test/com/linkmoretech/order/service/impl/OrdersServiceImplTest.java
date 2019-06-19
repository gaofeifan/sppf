package com.linkmoretech.order.service.impl;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.order.OrderServerApplicationTest;
import com.linkmoretech.order.enums.OrderTypeEnum;
import com.linkmoretech.order.service.OrdersService;
import com.linkmoretech.order.vo.OrderEditResponse;
import com.linkmoretech.order.vo.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;

/**
 * @Author: alec
 * @Description:
 * @date: 下午4:19 2019/4/15
 */
@Component
@Slf4j
public class OrdersServiceImplTest extends OrderServerApplicationTest {

    @Autowired
    OrdersService ordersService;

    @Test
    public void createOrder() throws CommonException {

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId("322424324126655");
        orderRequest.setOrderType(OrderTypeEnum.SUBSCRIBE.getCode());
        orderRequest.setParkingId(12L);
        orderRequest.setPlateId("23542543535");
        orderRequest.setParkingLotId("2354535343545");
        OrderEditResponse orderEditResponse =  ordersService.createOrder(orderRequest);
        log.info("create order is success orderId: {} ", orderEditResponse);
        Assert.assertTrue(orderEditResponse != null);
    }
}