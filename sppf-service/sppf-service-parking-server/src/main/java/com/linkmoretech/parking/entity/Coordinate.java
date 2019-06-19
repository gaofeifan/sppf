package com.linkmoretech.parking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 车场坐标
 * @Author: alec
 * @Description:
 * @date: 5:36 PM 2019/5/7
 */
@Entity
@Table(name = "p_coordinate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Integer type;

    private Long carParkId;


}
