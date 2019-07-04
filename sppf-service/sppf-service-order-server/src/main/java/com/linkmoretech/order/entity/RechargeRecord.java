package com.linkmoretech.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值记录实体
 * @Author: jhb
 * @Description:
 * @date: 下午7:36 2019/4/11
 */
@Entity
@Table(name = "o_recharge_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargeRecord {

    /*订单ID*/
    @Id
    @GeneratedValue
    private Long id;
    
    /*编号*/
    private String code;

    /*用户ID*/
    private Long userId;
    
    /*订单ID*/
    private String orderId;

    /*状态(0未完成，1已完成，-1已取消)*/
    private Short status;

    /*支付状态 1: 已支付 ；0 未支付*/
    private Short payStatus;

    /*支付类型：0代充，1微信、2支付宝*/
    private Short payType;

    /*实付金额*/
    private BigDecimal payAmount;

    /*订单支付时间*/
    private Date payTime;

    /*创建时间*/
    private Date createTime;

    /*修改时间*/
    private Date updateTime;

}
