package com.linkmoretech.auth.authentication.authentication.social.wechat.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.social.oauth2.AccessGrant;
/**
 * @Author: alec
 * Description: 处理微信响应消息
 * @date: 16:06 2019-07-02
 */
@Getter
@Setter
public class WeChatAccessGrant extends AccessGrant {

    private String openId;

    public WeChatAccessGrant(String accessToken) {
        super(accessToken);
    }

    public WeChatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }


}
