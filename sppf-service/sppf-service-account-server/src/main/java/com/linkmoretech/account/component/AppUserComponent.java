package com.linkmoretech.account.component;

import com.linkmoretech.account.entity.AppUser;
import com.linkmoretech.account.enums.ClientTypeEnum;
import com.linkmoretech.account.resposity.AppUserRepository;
import com.linkmoretech.auth.common.bean.AccountUserDetail;
import com.linkmoretech.auth.common.bean.AppUserDetail;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @Author: alec
 * Description:
 * @date: 14:00 2019-06-27
 */
@Component
public class AppUserComponent {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    AppUserRepository appUserRepository;

    public Long createUserId() {
        String userKey = "app_user_id";
        int step = 3;
        Long id = stringRedisTemplate.opsForValue().increment(userKey, step);
        return id;
    }


    public AppUser getAppUserById(Long userId) {

        Optional<AppUser> optional = appUserRepository.findById(userId);

        if (!optional.isPresent()) {
            try {
                throw new CommonException(ResponseCodeEnum.ERROR, "用户不存在");
            } catch (CommonException e) {
                e.printStackTrace();
            }
        }

        return optional.get();
    }
    public AppUserDetail getUserDetail (AppUser appUser, boolean isNewUser) {
        if (appUser == null) {
            return null;
        }
        AppUserDetail appUserDetail = new AppUserDetail(appUser.getUsername(),
                appUser.getPassword(),
                appUser.getUserId(),
                ClientTypeEnum.PERSONAL.getCode(), isNewUser);
        return appUserDetail;
    }
}
