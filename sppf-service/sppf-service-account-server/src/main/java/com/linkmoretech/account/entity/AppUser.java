package com.linkmoretech.account.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: alec
 * Description: 个人版用户
 * @date: 13:26 2019-05-31
 */
@Data
@Entity
@Table(name = "a_app_user")
public class AppUser {

    /**
     * 用户ID
     * 使用redis集群实现
     * */
    @Id
    private Long userId;

    /**
     * 手机号
     * */
    private String mobile;
    /**
     * 用户名
     * */
    private String username;

    /**
     * 用户微信OpenId
     * */
    private String openId;
    /**
     * 注册来源
     * */
    private Integer userSource;

    /**
     * 密码
     * */
    private String password;


    private Date createTime;


    private Date lastLoginTime;


}
