package com.linkmoretech.versatile.client;

import com.linkmoretech.auth.resource.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: alec
 * @Description:
 * @date: 2:08 PM 2019/5/7
 */
@FeignClient(name = "versatile", configuration = FeignConfiguration.class)
public interface AreaCityClient {

    /**
     * 根据城市编码查询该所有父节点编码
     * @param cityCode 城市编码
     * @return 当前城市及父节点的编码
     * */
    @GetMapping(value = "city/allCityCode")
    List<String> getAllCityCode(@RequestParam(value = "cityCode") String cityCode);

    /**
     * 根据城市编码获取城市名称
     * @param cityCode 城市编码
     * @return 城市名称
     * */
    @GetMapping(value = "city/getCityName")
    String getCityName(@RequestParam(value = "cityCode") String cityCode);
}
