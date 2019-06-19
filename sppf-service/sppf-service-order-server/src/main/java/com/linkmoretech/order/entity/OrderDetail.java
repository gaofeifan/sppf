package com.linkmoretech.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 订单明细数据
 * @Author: alec
 * @Description:
 * @date: 下午7:47 2019/4/11
 */
@Entity
@Table(name = "t_orders_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    @Id
    private String id;

    /*订单ID*/
    private String orderId;

    /*用户手机号*/
    private String mobile;

    /*停车场ID*/
    private String parkingId;

    /*停车场名称*/
    private String parkingName;

    /*车牌号*/
    private String plateNo;

    /**车位ID*/
    private String parkingLotId;

    /**车位名称*/
    private String parkingLotName;

    /*车锁状态*/
    private Integer lockStatus;

    /*车位状态*/
    private Integer parkingStatus;

    /*降锁驶入时间*/
    private Date sailInTime;

    /**检测驶出时间*/
    private Date sailOutTime;

    /*降锁时间*/
    private Date downLockTime;

    /*升锁时间*/
    private Date upLockTime;

    /*降锁是否成功*/
    private Integer downLockSuccess;

    /*升锁是否成功*/
    private Integer upLockSuccess;

}
