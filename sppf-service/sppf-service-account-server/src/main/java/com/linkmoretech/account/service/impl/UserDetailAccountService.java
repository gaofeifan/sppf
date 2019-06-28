package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.component.AccountComponent;
import com.linkmoretech.account.component.AppUserComponent;
import com.linkmoretech.account.entity.*;
import com.linkmoretech.account.enums.ClientTypeEnum;
import com.linkmoretech.account.resposity.*;
import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public UserDetails loadUserBy(String clientId, String username) {
        UserDetails userDetails;
        if (clientId.equals(ClientTypeEnum.PERSONAL.getCode())) {
            AppUser appUser = appUserRepository.getByMobile(username);
            userDetails = appUserComponent.getUserDetail(appUser);
        } else {
            User user = userRepository.getUserByClientIdAndUserName(clientId, username);
            userDetails = accountComponent.getUserDetail(user);
        }
        return userDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       return null;
    }
}
