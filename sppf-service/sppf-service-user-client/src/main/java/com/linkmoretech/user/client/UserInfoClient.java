package com.linkmoretech.user.client;

import com.linkmoretech.user.common.vo.UserInfoInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务提供的服务接口
 * @Author: alec
 * @Description:
 * @date: 上午11:00 2019/4/16
 */
@FeignClient(name = "user")
public interface UserInfoClient {
    /**
     * 创建用户
     * @param userInfoInput 创建用户参数
     * @return 用户ID
     * */
    @PostMapping(value = "user/create")
    String createUser(@RequestBody UserInfoInput userInfoInput);

    @Component
    @Slf4j
    class UserInfoFallBack implements UserInfoClient {
        @Override
        public String createUser(UserInfoInput userInfoInput) {
          log.error("user server is error request params {} ", userInfoInput);
          return null;
        }
    }
}
