package com.linkmoretech.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细数据
 * @Author: alec
 * @Description:
 * @date: 下午8:09 2019/4/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "订单明细 返回信息")
public class OrderDetailResponse {

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "订单类型")
    private Integer orderType;

    @ApiModelProperty(value = "用户手机号")
    private String mobile;

    @ApiModelProperty(value = "车场名称")
    private String parkingName;

    @ApiModelProperty(value = "车位名称")
    private String parkingLotName;

    @ApiModelProperty(value = "车位状态")
    private Integer parkingStatus;

    @ApiModelProperty(value = "车牌号")
    private String plateName;

    @ApiModelProperty(value = "预约时间")
    private Date createTime;

    @ApiModelProperty(value = "驶入时间")
    private Date sailInTime;

    @ApiModelProperty(value = "降锁时间")
    private Date downLockTime;

    @ApiModelProperty(value = "驶出时间")
    private Date sailOutTime;

    @ApiModelProperty(value = "升锁时间")
    private Date upLockTime;

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

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "支付类型")
    private Integer payType;


}
