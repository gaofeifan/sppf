package com.linkmoretech.auth.authentication.authentication.social.wechat.adapter;

import com.linkmoretech.auth.authentication.authentication.social.wechat.bean.WeChatUserInfo;
import com.linkmoretech.auth.authentication.authentication.social.wechat.inteface.WeChatApi;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @Author: alec
 * Description:
 * @date: 09:51 2019-07-11
 */
public class WeixinAdapter implements ApiAdapter<WeChatApi> {

    private String openId;

    public WeixinAdapter() {}

    public WeixinAdapter(String openId){
        this.openId = openId;
    }

    /**
     * 用来测试当前的API是否可用
     * @param api
     * @return
     */
    @Override
    public boolean test(WeChatApi api) {
        return true;
    }

    /**
     * 将微信的用户信息映射到ConnectionValues标准的数据化结构上
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(WeChatApi api, ConnectionValues values) {
        WeChatUserInfo profile = api.getWeChatUser(openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(WeChatApi api) {
        return null;
    }

    @Override
    public void updateStatus(WeChatApi api, String message) {

    }
}
