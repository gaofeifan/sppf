package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.component.AppUserComponent;
import com.linkmoretech.account.entity.AppUser;
import com.linkmoretech.account.enums.UserSourceEnum;
import com.linkmoretech.account.resposity.AppUserRepository;
import com.linkmoretech.account.service.AppUserService;
import com.linkmoretech.account.vo.request.AppUserRegisterRequest;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: alec
 * Description:
 * @date: 15:02 2019-06-27
 */
@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppUserComponent appUserComponent;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public AppUser register(AppUserRegisterRequest appUserRegisterRequest) throws CommonException {

        UserSourceEnum userSourceEnum = UserSourceEnum.getStatus(appUserRegisterRequest.getUserSource());

        if (userSourceEnum == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "用户注册来源不存在");
        }

        Integer existFlag = 1;

        boolean isExist = validateMobile(appUserRegisterRequest.getMobile(), existFlag);

        if (isExist) {
            throw new CommonException(ResponseCodeEnum.ERROR, "手机号已存在");
        }



        /**
         * 创建用户
         * */
        AppUser appUser = new AppUser();
        appUser.setMobile(appUserRegisterRequest.getMobile());
        appUser.setUserId(appUserComponent.createUserId());
        appUser.setUsername(appUserRegisterRequest.getMobile());
        appUser.setUserSource(userSourceEnum.getCode());
        int len = 10;
        appUser.setPassword(RandomStringUtils.randomNumeric(len));
        appUser.setCreateTime(new Date());
        AppUser resultUser =  appUserRepository.save(appUser);
        /**
         * 创建完成用户后调用用户服务将帐号信息同步至用户服务
         * */
        return resultUser;
    }

    @Override
    public AppUser register(String openId) throws CommonException {

        AppUser exitUser = appUserRepository.getByOpenId(openId);
        if (exitUser != null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "openId已存在");
        }
        AppUser appUser = new AppUser();
        appUser.setUserId(appUserComponent.createUserId());
        appUser.setUsername(openId);
        appUser.setUserSource(UserSourceEnum.WE_CHAR.getCode());
        int len = 10;
        appUser.setPassword(RandomStringUtils.randomNumeric(len));
        appUser.setCreateTime(new Date());
        AppUser resultUser =  appUserRepository.save(appUser);
        return resultUser;
    }

    @Override
    public boolean validateMobile(String mobile, Integer type) {

        AppUser appUser = appUserRepository.getByMobile(mobile);

        Integer existUser = appUser == null ? 0 : 1;

        return existUser.equals(type);
    }

    @Override
    public void setPassword(Long userId, String password) {

        AppUser appUser = appUserComponent.getAppUserById(userId);

        String newPassword = passwordEncoder.encode(password);

        appUser.setPassword(newPassword);

        appUserRepository.save(appUser);
    }

    @Override
    public AppUser getUser(String mobile) {
        AppUser appUser = appUserRepository.getByMobile(mobile);
        return appUser;
    }
}
