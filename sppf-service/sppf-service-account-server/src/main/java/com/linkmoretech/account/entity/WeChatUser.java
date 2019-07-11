package com.linkmoretech.account.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: alec
 * Description: 微信用户信息
 * @date: 14:08 2019-07-11
 */
@Data
@Entity
@Table(name = "a_wechat_user")
public class WeChatUser {

    @Id
    private String openId;

    private String unionId;

    private String nickName;

    private Integer gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private String appid;

    private String mobile;

    private String sessionKey;
}
