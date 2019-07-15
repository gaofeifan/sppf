package com.linkmoretech.account.component;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.account.entity.AppUser;
import com.linkmoretech.account.entity.WeChatUser;
import com.linkmoretech.account.enums.ClientTypeEnum;
import com.linkmoretech.account.resposity.AppUserRepository;
import com.linkmoretech.account.resposity.WeChatUserRepository;
import com.linkmoretech.auth.authentication.configuration.WeixinApiConfig;
import com.linkmoretech.auth.common.bean.AccountUserDetail;
import com.linkmoretech.auth.common.bean.AppUserDetail;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.http.util.HttpUtilComponent;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: alec
 * Description:
 * @date: 14:00 2019-06-27
 */
@Component
@Slf4j
public class AppUserComponent {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    HttpUtilComponent httpUtilComponent;

    @Autowired
    WeixinApiConfig weixinApiConfig;

    @Autowired
    WeChatUserRepository weChatUserRepository;

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
                log.error("command {}", e.getMessage());
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

    public AppUserDetail getUserDetail (WeChatUser weChatUser, boolean isNewUser)  {
        if (weChatUser == null) {
            try {
                throw new CommonException(ResponseCodeEnum.ERROR, "获取用户失败");
            } catch (CommonException e) {
                log.error("command {}", e.getMessage());
            }
        }

        AppUserDetail appUserDetail = new AppUserDetail(weChatUser.getOpenId(),
                weChatUser.getOpenId(),
                0L,
                ClientTypeEnum.PERSONAL.getCode(), isNewUser);
        return appUserDetail;
    }


    public WeChatUser loadUserByWechat(String code) throws CommonException {
        /**
         * 微信小程序登录
         * 换取token
         * */
        Map<String, String> params = new HashMap<>();
        params.put("appid", weixinApiConfig.getAppid());
        params.put("secret", weixinApiConfig.getSecret());
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        log.info("向微信发送换取token请求 url {} - {}", weixinApiConfig.getUrlLogin(), params);

        String responseValue =  httpUtilComponent.sendHttpGetRequest(weixinApiConfig.getUrlLogin(), params);
        log.info("响应码{}", responseValue);
        if (StringUtils.isEmpty(responseValue)) {
            throw new CommonException(ResponseCodeEnum.ERROR, "请求微信认证失败,错误码为空 ");
        }
        JSONObject jsonObject = JSONObject.parseObject(responseValue);
        Object responseCode = jsonObject.get("errcode");
        if (responseCode != null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "请求微信认证失败,错误码 " + responseCode.toString() );
        }
        String openId = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");

        /**
         * 校验用户信息是否存在
         * */
        WeChatUser weChatUser = weChatUserRepository.getByOpenId(openId);
        if (weChatUser == null) {
            /**
             * 表示当前用户是新用户，需要存储其 token 信息
             * */
            weChatUser = new WeChatUser();
            weChatUser.setOpenId(openId);
            weChatUser.setSessionKey(sessionKey);
            weChatUser = weChatUserRepository.save(weChatUser);
        }
        return weChatUser;
    }
}
