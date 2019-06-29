package com.linkmoretech.user.server;

import com.linkmoretech.common.annation.IgnoreResponseAdvice;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.exception.FeignException;
import com.linkmoretech.user.common.vo.UserInfoInput;
import com.linkmoretech.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: alec
 * @Description:
 * @date: 上午11:09 2019/4/16
 */
@RestController
@RequestMapping(value = "user-server")
@IgnoreResponseAdvice
public class UserInfoServer {

    @Autowired
    UserInfoService userInfoService;

    @PostMapping(value = "create")
    @IgnoreResponseAdvice
    public String createUser(@RequestBody UserInfoInput userInfoInput) throws FeignException {
        String userId = userInfoService.createUser(userInfoInput);
        return userId;
    }
}
