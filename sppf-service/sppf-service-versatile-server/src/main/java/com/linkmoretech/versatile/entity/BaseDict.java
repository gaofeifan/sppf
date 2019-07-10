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
 * @Author: jhb
 * @Description:
 * @date: 8:29 PM 2019/4/29
 */
@Data
@Entity
@Table(name = "v_base_dict")
@AllArgsConstructor
@NoArgsConstructor
public class BaseDict {

    @Id
    @GeneratedValue
    private Long id;

    private Long groupId;

    private String name;

    private String code;

    private int orderIndex;

    private int extra;

    private Date createTime;

    private Date updateTime;
}
