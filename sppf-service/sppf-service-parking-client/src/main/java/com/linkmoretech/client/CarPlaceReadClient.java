package com.linkmoretech.client;

import com.linkmoretech.auth.common.configuration.FeignConfiguration;
import com.linkmoretech.parking.common.PlaceParkIdAndRangeInput;
import com.linkmoretech.parking.common.PlaceParkIdAndRangeOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: GFF
 * @Description: 内部调用--车位服务接口
 * @Date: 2019/6/25
 */
@FeignClient(name = "parking", configuration = FeignConfiguration.class)
public interface CarPlaceReadClient {

    /**
     * @Author GFF
     * @Description  根据车场id与车位区间查询
     * @Date 2019/6/25
     */
    @PostMapping(value = "car/palce/read/park-id-and-id-range")
    List<PlaceParkIdAndRangeOutput> findByParkIdAndIdRange(@RequestBody PlaceParkIdAndRangeInput input);

}
