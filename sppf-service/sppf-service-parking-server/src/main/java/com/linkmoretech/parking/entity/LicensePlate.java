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
 * @date: 11:32 AM 2019/4/29
 */
@Entity
@Table(name = "p_license_plate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicensePlate implements Serializable, Cloneable {

    @Id
    @GeneratedValue
    private Long id ;

    private String leaseCode;

    /** 车场ID */
    private Long parkId;


    private Long placeId;
    /**
     * 车位编号
     * */
    private String placeNo;

    /** 车牌号 */
    private String licensePlateNo ;

    /** 可用状态 */
    private Integer plateStatus ;
    
}
