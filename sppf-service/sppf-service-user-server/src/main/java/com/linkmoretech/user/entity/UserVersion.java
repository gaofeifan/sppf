package com.linkmoretech.user.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户版本信息
 * @author jhb
 * @Date 2019年6月27日 上午10:48:36
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user_version")
public class UserVersion {
	@Id
    private String userId;
    //1.安卓2.ios
    private Short client;
    //手机型号
    private String model;
    //TODO
    private String username;
    //系统版本号
    private String osVersion;

    private String uuid;
    //app版本号
    private String version;
    //提交时间
    private Date commitTime;

    private Integer system;
    
}