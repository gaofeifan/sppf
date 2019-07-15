package com.linkmoretech.station.controller;

import com.linkmoretech.station.service.RealTimeQueryService;
import com.linkmoretech.station.vo.response.RealDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: alec
 * Description: 首页定时显示各个数据
 * @date: 15:19 2019-07-15
 */
@RestController
public class HomeController {

    @Autowired
    RealTimeQueryService realTimeQueryService;

    @RequestMapping(value = "main")
    public RealDataResponse analysis () {
        return realTimeQueryService.realTime();
    }
}
