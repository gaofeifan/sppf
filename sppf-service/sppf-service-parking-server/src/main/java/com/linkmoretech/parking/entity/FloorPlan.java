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
 * @date: 11:24 AM 2019/4/29
 */
@Entity
@Table(name = "p_floor_plan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FloorPlan implements Serializable,Cloneable {

    @Id
    @GeneratedValue
    private Long id ;

    /** 车场ID;车场主键ID */
    private Long parkId ;

    /** 分布图名称;分布图名称 */
    private String floorName ;

    /** 车位数;该分布图车位数 */
    private Integer placeNumber ;

    /** 地图ID;对应地图ID */
    private Long mapId ;

    /** 地图名称;对应地图名称(冗余字段) */
    private String mapName ;

    /** 创建时间;创建时间 */
    private Date createdTime ;

    /** 更新时间;更新时间 */
    private Date updatedTime ;
}
