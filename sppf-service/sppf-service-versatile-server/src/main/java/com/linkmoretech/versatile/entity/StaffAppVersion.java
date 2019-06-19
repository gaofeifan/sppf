package com.linkmoretech.versatile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 管理版版本管理
 * @Author: jhb
 * @Description:
 * @date: 8:29 PM 2019/4/29
 */
@Data
@Entity
@Table(name = "v_staff_app_version")
@AllArgsConstructor
@NoArgsConstructor
public class StaffAppVersion {

    @Id
    @GeneratedValue
    private Long id;

    private String version;

    private Long code;

    private String name;

    private Integer status;

    private String url;

    private Integer type;

    private Integer updateStatus;

    private Date createTime;

    private String description;

    private Date updateTime;

}
