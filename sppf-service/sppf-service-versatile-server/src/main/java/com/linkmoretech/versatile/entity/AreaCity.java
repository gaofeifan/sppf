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
 * 城市区域
 * @Author: alec
 * @Description:
 * @date: 8:29 PM 2019/4/29
 */
@Data
@Entity
@Table(name = "v_area_city")
@AllArgsConstructor
@NoArgsConstructor
public class AreaCity {

    @Id
    @GeneratedValue
    private Long id;

    private String cityCode;

    private String cityName;

    private Long parentId;

    private Date createTime;

    private Date updateTime;
}
