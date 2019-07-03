package com.linkmoretech.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.linkmoretech.auth.common.configuration.FeignConfiguration;
import com.linkmoretech.user.client.fallback.UserInfoFallBackFactory;
import com.linkmoretech.user.common.vo.UserInfoInput;

/**
 * 用户服务提供的服务接口
 * @Author: alec
 * @Description:
 * @date: 上午11:00 2019/4/16
 */
@FeignClient(name = "user", configuration = FeignConfiguration.class, fallbackFactory = UserInfoFallBackFactory.class)
public interface UserInfoClient {
    /**
     * 创建用户
     * @param userInfoInput 创建用户参数
     * @return 用户ID
     * */
    @PostMapping(value = "user-info/create")
    String createUser(@RequestBody UserInfoInput userInfoInput);
}
