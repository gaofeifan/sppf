package com.linkmoretech.account.component;

import com.linkmoretech.account.entity.Resources;
import com.linkmoretech.account.entity.User;
import com.linkmoretech.account.enums.EnableStatusEnum;
import com.linkmoretech.account.resposity.UserRepository;
import com.linkmoretech.auth.common.bean.AccountUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description:
 * @date: 15:02 2019-06-04
 */
@Component
@Slf4j
public class AccountComponent {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    UserComponent userComponent;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponseBean dealUserLogin (User user) {
        UserResponseBean userResponseBean = new UserResponseBean();
        String[] strArray;
        Set<Long> dataSet = new HashSet<>();
        if (user.getStatus().equals(EnableStatusEnum.ENABLED.getCode())) {
            List<Resources> resourcesList = userComponent.getResourceIdListByUser(user);
            List<String> resourceValue = resourcesList.stream().map(Resources::getRouterName).collect(Collectors.toList());
            strArray = resourceValue.toArray(new String[resourceValue.size()]);
            userResponseBean.setOptionResources(strArray);
            userResponseBean.setDataResources(dataSet);
            user.setLastLoginTime(new Date());
            userRepository.save(user);
        }
        return userResponseBean;
    }

    public AccountUserDetail getUserDetail(User user) {
        if (user == null) {
            return null;
        }
        UserResponseBean userResponseBean = dealUserLogin(user);
        String[] resources = userResponseBean.getOptionResources();
        if (resources == null) {
          resources = new String[0];
        }
        AccountUserDetail userDetails = new AccountUserDetail(user.getUserName(),
                user.getPassword(),
                user.getStatus().equals(EnableStatusEnum.ENABLED.getCode()),
                AuthorityUtils.createAuthorityList(resources),
                user.getId(), user.getClientId());

        return userDetails;
    }



}
