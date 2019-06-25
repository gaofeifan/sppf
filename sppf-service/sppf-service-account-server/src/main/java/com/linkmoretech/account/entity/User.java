package com.linkmoretech.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: alec
 * Description: 后台帐号
 * @date: 13:26 2019-05-31
 */
@Entity
@Table(name = "a_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    /**登陆帐号*/
    private String userName;

    /**登陆密码*/
    private String password;

    /**手机号*/
    private String mobile;

    /**客户端ID*/
    private String clientId;

    /**用户状态*/
    private Integer status;

    /**数据权限*/
    private Integer authStatus;

    /**最近登陆时间*/
    private Date lastLoginTime;

    private Date createTime;

    private Date updateTime;
}
