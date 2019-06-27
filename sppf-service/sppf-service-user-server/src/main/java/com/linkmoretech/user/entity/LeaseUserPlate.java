package com.linkmoretech.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 长租用户车牌号使用
 * @Author: alec
 * @Description:
 * @date: 下午6:26 2019/4/15
 */
@Entity
@Table(name = "t_rental_user_plate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaseUserPlate {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 长租授权码
     * */
    private String leaseCode;

    /**
     * 车牌号
     * */
    private String licensePlate;
}
