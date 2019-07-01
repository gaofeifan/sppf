package com.linkmoretech.account.controller;

import com.linkmoretech.account.entity.AuthUserPark;
import com.linkmoretech.account.service.AppUserService;
import com.linkmoretech.account.vo.request.AppUserRegisterRequest;
import com.linkmoretech.account.vo.request.PasswordEditRequest;
import com.linkmoretech.auth.common.util.AuthenticationTokenAnalysis;
import com.linkmoretech.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: alec
 * Description: APP 用户登录注册
 * @date: 14:49 2019-06-27
 */
@RestController
@RequestMapping(value = "app")
@Slf4j
public class AppUserController {

    @Autowired
    AppUserService appUserService;

    /**
     * 验证用户信息是否合法
     * 1。验证用户是否存在
     * 2。验证用户是否不存在
     * */
    @GetMapping(value = "validate")
    public Boolean validateMobile(@RequestParam(value = "mobile") String mobile,
                                  @RequestParam(value = "type") Integer type) {
        return appUserService.validateMobile(mobile, type);
    }

    @PostMapping(value = "setting/password")
    public void register(Authentication authentication,
                         @RequestBody PasswordEditRequest passwordEditRequest) throws CommonException {
        AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
        log.info("修改密码 {}" , passwordEditRequest.getPassword());
        log.info("用户信息 {}", authenticationTokenAnalysis.getUserId());
        appUserService.setPassword(authenticationTokenAnalysis.getUserId(), passwordEditRequest.getPassword());
    }




}
