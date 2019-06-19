package com.linkmoretech.parking.component;

import com.linkmoretech.common.util.CodeUtil;
import com.linkmoretech.common.util.MdUtil;
import com.linkmoretech.parking.entity.CarPark;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description: 长租车位唯一码生成器
 * @date: 20:33 2019-05-21
 */
@Component
@Slf4j
public class LeaseOrderComponent {

    /**
     * 生成唯一码 规则
     * 车场ID加密
     * 时间戳
     * 随机码
     *
     * */
    public String getLeaseCode(CarPark carPark) {
        long carParkId = carPark.getId();
        int initRandomBound = 9999;
        int initTimeBound = 8;
        String orderIdPre = carParkId + CodeUtil.getRandomValue(initRandomBound);
        orderIdPre = orderIdPre + CodeUtil.timeStampAfter(initTimeBound);
        orderIdPre = CodeUtil.getReverseValue(orderIdPre);
        System.out.println(orderIdPre);
        log.info("生成授权码{}", orderIdPre);
        return MdUtil.encode(orderIdPre);
    }
}
