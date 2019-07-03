package com.linkmoretech.auth.authentication.authentication.social.wechat.inteface;

import com.linkmoretech.auth.authentication.authentication.social.wechat.bean.WeChatUserInfo;

/**
 * @Author: alec
 * Description: 定义获取微信用户API
 * @date: 14:12 2019-07-02
 */
public interface WeChatApi {

    /**
     * 根据微信用户openID 获取微信用户
     * @param openId 微信唯一表示
     * @return 微信用户信息
     * */
    WeChatUserInfo getWeChatUser(String openId);
}
