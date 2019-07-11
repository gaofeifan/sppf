package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.component.AccountComponent;
import com.linkmoretech.account.component.AppUserComponent;
import com.linkmoretech.account.entity.*;
import com.linkmoretech.account.enums.ClientTypeEnum;
import com.linkmoretech.account.resposity.*;
import com.linkmoretech.account.service.AppUserService;
import com.linkmoretech.account.vo.request.AppUserRegisterRequest;
import com.linkmoretech.auth.common.exception.RegisterException;
import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import com.linkmoretech.common.exception.CommonException;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * @Author: alec
 * Description:
 * @date: 11:58 2019-06-13
 */
@Service
@Slf4j
public class UserDetailAccountService extends UserDetailAccountAbstract {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesResourcesRepository rolesResourcesRepository;

    @Autowired
    UserRolesRepository userRolesRepository;

    @Autowired
    ResourcesRepository resourcesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountComponent accountComponent;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppUserComponent appUserComponent;

    @Autowired
    AppUserService appUserService;


    @Override
    public UserDetails loadUserBy(String clientId, String username) {
        UserDetails userDetails;
        if (clientId.equals(ClientTypeEnum.PERSONAL.getCode())) {
            AppUser appUser = appUserRepository.getByMobile(username);
            userDetails = appUserComponent.getUserDetail(appUser, false);
        } else {
            User user = userRepository.getUserByClientIdAndUserName(clientId, username);
            userDetails = accountComponent.getUserDetail(user);
        }
        return userDetails;
    }

    @Override
    public UserDetails register(String mobile, Integer type) throws RegisterException {
        log.info("处理用户注册逻辑");
        AppUserRegisterRequest registerRequest = new AppUserRegisterRequest();
        registerRequest.setMobile(mobile);
        registerRequest.setUserSource(type);
        try {
            AppUser appUser = appUserService.register(registerRequest);
            return appUserComponent.getUserDetail(appUser, true);
        } catch (CommonException e) {
            log.error("登录异常 {}", e.getMessage());
            log.error("登录异常 {}", e.getCodeEnum().getCode());
            throw new RegisterException(e.getCodeEnum().getCode(), e.getMessage());
        }
    }

    @Override
    public UserDetails login(String mobile, Integer type) throws RegisterException {
        AppUser appUser = appUserService.getUser(mobile);
        if (appUser == null) {
            return register(mobile, type);
        }
        log.info("处理用户登录逻辑");
        return appUserComponent.getUserDetail(appUser, false);
    }

    @Override
    public UserDetails loginForWechat(String code) throws RegisterException {
        log.info("微信小程序调用登录 {}", code);
        try {
            WeChatUser weChatUser = appUserComponent.loadUserByWechat(code);
            if (StringUtils.isEmpty(weChatUser.getMobile())) {
                /**
                 * 需要创建帐号
                 * */
                AppUser appUser =  appUserService.register(weChatUser.getOpenId());
                return appUserComponent.getUserDetail(appUser, true);
            }
            AppUser appUser = appUserRepository.getByOpenId(weChatUser.getOpenId());
            return appUserComponent.getUserDetail(appUser, false);
        } catch (CommonException e) {
            throw new RegisterException(e.getCodeEnum().getCode(), e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       return null;
    }
}
