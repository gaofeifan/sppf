package com.linkmoretech.parking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: alec
 * @Description:
 * @date: 11:14 AM 2019/4/29
 */
@Entity
@Table(name = "p_car_park")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPark implements Serializable,Cloneable {

    @Id
    @GeneratedValue
    private Long id ;

    /** 车场名称 */
    private String parkName ;

    /** 车场总车位数 */
    private Integer placeNumber ;

    /** 车场已装车锁车位数 */
    private Integer lockNumber ;

    /** 车场类型包含：1:临停车位, 2:固定车位，3:组合车位 */
    private Integer parkType ;

    /** 车场可被使用的时间段开始时间(每天) */
    private String startTime ;

    /**车场可被使用的时间段(结束时间) */
    private String endTime ;

    /** 车场计费策略ID */
    private String chargeCode;

    /** 车场计费策略名称 */
    private String chargeName ;

    /** 车场所在城市编码 */
    private String cityCode ;

    /** 车场所在城市名称 */
    private String cityName ;

    /** 车场所在具体地理位置 */
    private String location ;

    /** 车场是否可以使用临停车位 */
    private Integer fixedStatus ;

    /** 车场是否可用使用长租车位 */
    private Integer tempStatus ;

    /** 创建人 */
    private String createBy ;

    /** 创建时间 */
    private Date createTime ;

    /** 更新人 */
    private String updateBy ;

    /** 更新时间 */
    private Date updateTime ;

    /** 锁平台绑定分组*/
    private String lockGroupCode;
}
