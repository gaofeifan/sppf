package com.linkmoretech.parking.component;

import com.linkmoretech.parking.entity.LicensePlate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: alec
 * Description: 长租车位，车牌号绑定关系
 * @date: 13:48 2019-05-24
 */
@Slf4j
@Component
public class LeasePlateComponent {

    private static final String LEASE_PLACE_STOCK_TEMPLATE = "license_lease_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 绑定车牌号 和 车位 关系，同时绑定的车位和车牌关系
     * */
    public void bindPlateForLicensePlace(List<LicensePlate> licensePlates) {


        /**
         * 对车牌分组
         * */


        /**
         * 对车位分组
         * */

    }
}
