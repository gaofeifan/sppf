package com.linkmoretech.account.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 14:00 2019-06-27
 */
@Component
public class AppUserComponent {

    private final Integer STEP = 3;

    private final String USER_KEY = "app_user_id";

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public Long createUserId() {
        Long id = stringRedisTemplate.opsForValue().increment(USER_KEY, STEP);
        return id;
    }
}
