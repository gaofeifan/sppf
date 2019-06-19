package com.linkmoretech.order.config;

import com.linkmoretech.common.util.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * @Description:
 * @date: 下午12:01 2019/4/15
 */
@Component
@Slf4j
public class GeneratedIdComponent {

    /**
     * ID生成策略
     * 订单生成策略 订单来源（1）+ 用户ID（4） + (时间戳)8  + 随机数（2）+ 时间ID（4）
     * @param userId 用户ID
     * @param orderType 订单类型
     * @return 订单ID
     * */

    public String getOrderId(String userId, Integer orderType) {
       String orderId = getOrderIdPre(orderType);
       orderId = orderId + getOrderIdLast(userId);
       return orderId;
    }

    public String getOrderDetailId(String userId, Integer orderType) {
        String orderId = getOrderIdPre(orderType);
        orderId = orderId + CodeUtil.getOrderDate();
        orderId = orderId + getOrderIdLast(userId);
        return orderId;
    }

    /**
     * 获取订单号头
     * @param orderType 订单类型
     * */
    private String getOrderIdPre(Integer orderType) {
        int initRandomBound = 99;
        int initTimeBound = 8;
        String orderIdPre = orderType + CodeUtil.getRandomValue(initRandomBound);
        orderIdPre = orderIdPre + CodeUtil.timeStampAfter(initTimeBound);
        return orderIdPre;
    }

    /**
     * 获取订单号尾
     * */
    private String getOrderIdLast(String userId) {
        return  CodeUtil.getReverseValue(userId.substring(userId.length() - 4));
    }

}
