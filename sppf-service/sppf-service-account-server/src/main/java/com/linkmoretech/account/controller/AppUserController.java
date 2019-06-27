package com.linkmoretech.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: alec
 * Description: APP 用户登录注册
 * @date: 14:49 2019-06-27
 */
@RestController
@RequestMapping(value = "app")
public class AppUserController {


    @GetMapping(value = "validate")
    public Boolean validateMobile() {

        return null;
    }

}
