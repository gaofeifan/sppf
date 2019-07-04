package com.linkmoretech.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单数据库实体
 * @Author: alec
 * @Description:
 * @date: 下午7:36 2019/4/11
 */
@Entity
@Table(name = "o_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    /*订单ID*/
    @Id
    private String id;

    /*用户ID*/
    private Long userId;

    /*订单状态*/
    private Integer status;

    /*订单类型*/
    private Integer orderType;

    /*支付类型*/
    private Integer payType;

    /*减免类型*/
    private Integer reductionType;

    /*订单金额*/
    private BigDecimal totalAmount;

    /*实付金额*/
    private BigDecimal payAmount;

    /*减免金额*/
    private BigDecimal reductionAmount;

    /*订单完成时间*/
    private Date finishTime;

    /*订单支付时间*/
    private Date payTime;

    /*交易取消时间*/
    private Date cancelTime;

    /*挂起或者关闭时间*/
    private Date statusTime;

    /*创建时间*/
    private Date createTime;

    /*修改时间*/
    private Date updateTime;
    
    /*客户端类型：客户端类型[0小程序、1Android、2IOS]*/
    private Integer clientType;

}
