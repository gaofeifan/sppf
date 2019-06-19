package com.linkmoretech.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: alec
 * Description: 系统权限资源
 * @date: 16:07 2019-05-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "a_resource")
public class Resources {

    @Id
    @GeneratedValue
    private Long id;

    private Long parentId;

    private String clientId;

    private String resourceCode;

    private Integer resourceType;

    private String resourceName;

    private String routerName;

    private Integer resourceStatus;

    private Integer resourceSort;

    private Date createTime;

    private Date updateTime;
}
