package com.linkmoretech.parking.entity;

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
 * Description: 长租车位记录表
 * @date: 17:47 2019-05-08
 */
@Entity
@Table(name = "p_lease_place")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasePlace {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * 长租码
     * */
    private String leaseCode;

    private Long parkId ;

    private Long placeId;
    /** 车位号 */
    private String placeNo ;

    /** 车场名称;对应车场名称(冗余字段) */
    private String parkName ;

    /** 联系电话 */
    private String linkMobile ;

    /** 生效开始日期 */
    private Date startDate ;

    /** 生效截止日期 */
    private Date endDate ;

    /** 长租车位状态:1启用 2禁用 */
    private Integer leaseStatus ;

    /** 创建人 */
    private String createBy ;

    /** 创建时间 */
    private Date createTime ;

    /** 更新人 */
    private String updateBy ;

    /** 更新时间 */
    private Date updateTime ;

}
