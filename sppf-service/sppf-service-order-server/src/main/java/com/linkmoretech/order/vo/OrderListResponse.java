package com.linkmoretech.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单列表返回数据
 * @Author: alec
 * @Description:
 * @date: 下午8:09 2019/4/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "订单列表 返回信息")
public class OrderListResponse {

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "订单类型")
    private Integer orderType;

    @ApiModelProperty(value = "用户手机号")
    private String mobile;

    @ApiModelProperty(value = "车场名称")
    private String parkName;

    @ApiModelProperty(value = "车位名称")
    private String placeName;

    @ApiModelProperty(value = "车牌号")
    private String plateNo;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "车锁状态")
    private Integer lockStatus;

    @ApiModelProperty(value = "车位状态")
    private Integer placeStatus;

    @ApiModelProperty(value = "停车时长")
    private Long duration;

    @ApiModelProperty(value = "订单总额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "实付金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "减免金额")
    private BigDecimal reductionAmount;

    @ApiModelProperty(value = "减免类型")
    private Integer reductionType;
}
