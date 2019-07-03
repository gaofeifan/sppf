package com.linkmoretech.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户信息实体类
 * @Author: alec
 * @Description:
 * @date: 下午3:16 2019/4/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user_info")
public class UserInfo {
    @Id
    private Long id;
    /**用户账号*/
    private String userName;
    /**手机号*/
    private String userMobile;
    /**用户昵称*/
    private String userNick;
    /**用户性别*/
    private Integer userSex;
    /**用户来源*/
    private Integer userSource;
    /**用户状态1正常 2冻结*/
    private Integer userStatus;
    /**用户类型*/
    private Integer userType;
    /**车辆型号*/
    private String vehicleType;
    /**车辆品牌*/
    private String vehicleBrand;
    /**注册时间*/
    private Date registerTime;
    /**创建时间*/
    private Date createTime;
    /**修改时间*/
    private Date updateTime;
    /**微信绑定状态*/
    private Integer weChatBindState;
    /**微信绑定账号*/
    private String openId;
    /**微信绑定时间*/
    private Date weChatBindTime;
    /**微信用户头像*/
    private String wechatIcon;
    /**微信昵称*/
    private String wechatName;
    
    private String updateBy;
}
