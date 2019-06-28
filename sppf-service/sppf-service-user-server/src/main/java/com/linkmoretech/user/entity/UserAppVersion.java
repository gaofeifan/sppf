package com.linkmoretech.user.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_user_app_version")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAppVersion {
	@Id
    @GeneratedValue
    private Long id;
    //版本号
    private String version;
    //版本代号
    private Long code;
    //版本名
    private String name;
    //此版本是否为有效的，1有效
    private Integer status;
    //下载地址
    private String url;
    //适用客户端：1安卓；2ios；
    private Integer type;
    //升级范围默认全部
    private Integer scope;
    //是否必须升级；1是
    private Integer updateStatus;
    //版本创建时间
    private Date createTime;
    //版本描述
    private String description;
    //版本更新时间
    private Date updateTime;

}