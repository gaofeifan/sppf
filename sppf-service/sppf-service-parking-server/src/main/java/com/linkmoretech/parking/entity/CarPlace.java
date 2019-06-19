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
 * @date: 11:20 AM 2019/4/29
 */
@Entity
@Table(name = "p_car_place")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPlace implements Serializable,Cloneable {

    @Id
    @GeneratedValue
    private Long id ;

    /** 车场ID;所在车场ID */
    private Long parkId ;

    /** 车场名称;所在车场名称 */
    private String parkName ;

    /** 平面图ID;车位平面图ID */
    private Long floorPlanId ;

    /** 平面图名称;车位所在层(冗余字段) */
    private String floorPlanName ;

    /** 车位计费ID;计费ID */
    private String chargeCode ;

    /** 车位计费名称;计费名称(冗余字段) */
    private String chargeName ;

    /** 车位编号;车位编号 */
    private String placeNo ;

    /** 地锁编号;车位安装地锁编号 */
    private String lockCode ;

    /** 地锁电量;地锁电量 */
    private Integer electric ;

    /** 地锁状态;地锁状态: 1:升起,2:降下, 3:故障 */
    private Integer lockStatus ;

    /** 车位状态;车位状态: 1:空闲，2:使用 */
    private Integer placeStatus ;

    /** 车位类型;车位类型: 1:固定， 2:临停 */
    private Integer placeType ;

    /** 上线状态;上线状态: 1: 上线，2:下线 */
    private Integer lineStatus ;

    /** 创建人;创建人 */
    private String createBy ;

    /** 创建时间;创建时间 */
    private Date createTime ;

    /** 更新人;更新人 */
    private String updateBy ;

    /** 更新时间;更新时间 */
    private Date updateTime ;
}
