package com.linkmoretech.versatile.controller;

import com.linkmoretech.versatile.service.ChargeService;
import com.linkmoretech.versatile.vo.response.ChargeListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: alec
 * Description: 计费
 * @date: 17:36 2019-05-20
 */
@RestController(value = "ChargeController")
@RequestMapping("charge")
public class ChargeController {

    @Autowired
    ChargeService chargeService;

    @GetMapping(value = "list")
    public List<ChargeListResponse> list() {
        return chargeService.list();
    }
}
