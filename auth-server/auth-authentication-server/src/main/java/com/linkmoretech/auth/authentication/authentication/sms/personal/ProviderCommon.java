package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.common.bean.AppUserDetail;
import com.linkmoretech.auth.common.exception.RegisterException;
import com.linkmoretech.auth.common.service.AppUserDetailAbstract;
import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import com.linkmoretech.auth.common.token.AppAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * @Author: alec
 * Description:
 * @date: 19:56 2019-06-28
 */
public class ProviderCommon {

    AppAuthenticationToken token;

    public ProviderCommon (Authentication authentication) {
        this.token = (AppAuthenticationToken) authentication;
    }

    public AppAuthenticationToken getUserDetailForLogin (UserDetailAccountAbstract userDetailService) {
        String mobile =getMobile();
        Integer type = getType();
        AppUserDetail userDetails = (AppUserDetail) userDetailService.login(mobile, type);
        return build(userDetails);
    }


    public AppAuthenticationToken getUserDetailForRegister (UserDetailAccountAbstract userDetailService) throws RegisterException {
        String mobile =getMobile();
        Integer type = getType();
        AppUserDetail userDetails = (AppUserDetail) userDetailService.register(mobile, type);
        return build(userDetails);
    }

    private String getMobile () {
        return (String)token.getPrincipal();
    }

    private Integer getType () {
        return token.getType();
    }

    private AppAuthenticationToken build (AppUserDetail userDetails) {
        AppAuthenticationToken appAuthenticationToken = new AppAuthenticationToken(userDetails.getUsername(),
                userDetails.getUserId(), userDetails.getRegister());
        appAuthenticationToken.setDetails(token.getDetails());
        return appAuthenticationToken;
    }
}
