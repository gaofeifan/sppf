package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.component.AccountComponent;
import com.linkmoretech.account.entity.User;
import com.linkmoretech.account.resposity.*;
import com.linkmoretech.auth.common.service.UserDetailMobileAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * @Author: alec
 * Description:
 * @date: 17:31 2019-06-18
 */
@Service
@Slf4j
public class UserDetailMobileService extends UserDetailMobileAbstract {

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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("自定义手机号验证");
        return null;
    }

    @Override
    public UserDetails loadUserBy(String clientId, String username) {
        User user = userRepository.getUserByClientIdAndMobile(clientId, username);
        UserDetails userDetails = accountComponent.getUserDetail(user);
        return userDetails;
    }
}
