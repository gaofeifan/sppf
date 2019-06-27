package com.linkmoretech.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 车牌号实体类
 * @Author: alec
 * @Description:
 * @date: 下午3:18 2019/4/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_license_plate")
public class LicensePlate {
    @Id
    @GeneratedValue
    private Long id;

    /**车牌号*/
    private String plateNo;

    /**用户ID*/
    private String userId;

    /**车牌类型*/
    private Integer plateType;

    private Date createTime;

    private Date updateTime;
}
