package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.component.UserComponent;
import com.linkmoretech.account.entity.User;
import com.linkmoretech.account.resposity.UserRepository;
import com.linkmoretech.account.service.AccountService;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: alec
 * Description:
 * @date: 20:32 2019-06-19
 */
@Service
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

        userRepository.save(user);
    }

    @Override
    public void updatePassword(String clientId, String username, String password, String oldPassword) throws CommonException {
        User user = userComponent.validateUser(clientId,username);
        if (user == null) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "用户不存在");
        }
        if (!passwordEncoder.matches(user.getPassword(), oldPassword)) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "原密码不正确");
        }
        updatePassword(clientId, username, password);
    }


}
