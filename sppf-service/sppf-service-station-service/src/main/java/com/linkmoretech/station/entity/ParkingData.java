package com.linkmoretech.station.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: alec
 * Description:
 * @date: 15:47 2019-07-15
 */
@Data
@Entity
@Table(name = "s_parking_data")
public class ParkingData {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 车场名称
     * */
    private String parkName;

    /**
     * 车场编号
     * */
    private String parkCode;

    /**
     * 临停车位数
     * */
    private Integer tempNum;

    /**
     * 固定车位数
     * */
    private Integer fixedNum;

    private Integer sort;


}
