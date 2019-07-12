package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.component.UserComponent;
import com.linkmoretech.account.entity.User;
import com.linkmoretech.account.enums.ClientTypeEnum;
import com.linkmoretech.account.resposity.UserRepository;
import com.linkmoretech.account.service.AccountService;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: alec
 * Description:
 * @date: 20:32 2019-06-19
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserComponent userComponent;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void updatePassword(String clientId, String username, String password) throws CommonException {

        User user = userComponent.validateUser(clientId,username);
        if (user == null) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "用户不存在");
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setUpdateTime(new Date());
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String clientId, String username, String password, String oldPassword) throws CommonException {
        User user = userComponent.validateUser(clientId,username);

        log.info("oldPassword {}, new password {}, oldPassword {}", user.getPassword(), password, oldPassword);

        if (user == null) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "用户不存在");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "原密码不正确");
        }
        updatePassword(clientId, username, password);
    }

    @Override
    public void registerMobile(Long userId, String mobile) throws CommonException {

        User user = userRepository.getUserByClientIdAndMobile(ClientTypeEnum.PERSONAL.getCode(), mobile);

        if (user != null) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "手机号已存在");
        }

        user =  userComponent.getUser(userId);

        if (user == null) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "用户已存在");
        }

        user.setMobile(mobile);
        user.setUpdateTime(new Date());
        userRepository.save(user);
    }


}
