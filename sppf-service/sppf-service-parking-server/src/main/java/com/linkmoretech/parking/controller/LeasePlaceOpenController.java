package com.linkmoretech.parking.controller;

import com.linkmoretech.common.annation.IgnoreResponseAdvice;
import com.linkmoretech.parking.common.LeaseInput;
import com.linkmoretech.parking.common.LeaseOutput;
import com.linkmoretech.parking.service.LeasePlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: alec
 * Description: 长租车位对其他服务提供
 * @date: 13:54 2019-05-22
 */
@RestController(value = "LeasePlaceOpenController")
@RequestMapping(value = "lease/open")
public class LeasePlaceOpenController {

    @Autowired
    LeasePlaceService leasePlaceService;

    @PostMapping(value = "place-list")
    @IgnoreResponseAdvice
    public LeaseOutput getLeasePlaceList(@RequestBody LeaseInput leaseInput) {
        return leasePlaceService.getLeasePlaceList(leaseInput);
    }
}
