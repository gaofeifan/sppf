package com.linkmoretech.client;

import com.linkmoretech.auth.common.configuration.FeignConfiguration;
import com.linkmoretech.parking.common.LeaseInput;
import com.linkmoretech.parking.common.LeaseOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: alec
 * Description: 定义长租车位服务接口
 * @date: 13:40 2019-05-22
 */
@FeignClient(name = "parking", configuration = FeignConfiguration.class)
public interface LeasePlaceClient {

    /**
     * 根据车牌号查询该车牌的长租车位信息
     * @param leaseInput 用户车牌号
     * @return 长租车场，车位记录
     * */
    @PostMapping(value = "lease/open/place-list")
    LeaseOutput getLeasePlaceList(@RequestBody LeaseInput leaseInput);

    @Component
    @Slf4j
    class LeasePlaceFallBack implements LeasePlaceClient {
        @Override
        public LeaseOutput getLeasePlaceList(@RequestBody LeaseInput leaseInput) {
            log.error("调用车场服务获取车牌号对应长租车位信息服务出错 {}", leaseInput);
            return null;
        }
    }
}
