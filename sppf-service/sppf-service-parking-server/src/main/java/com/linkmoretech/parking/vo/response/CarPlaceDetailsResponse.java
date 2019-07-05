package com.linkmoretech.parking.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: GFF
 * @Description: ${description}  车位详情响应数据
 * @Date: 2019/6/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarPlaceDetailsResponse {
    @ApiModelProperty("车位id")
    private Long carPlaceId;
    @ApiModelProperty("车位名称")
    private String carPlaceName;
    @ApiModelProperty("分区名称")
    private String areaName;
    @ApiModelProperty("锁编号")
    private String lockSn;
    @ApiModelProperty("锁状态(1:竖起状态 2：躺下 3, 故障")
    private Integer lockStatus;
    @ApiModelProperty("车位状态 状态:0，空闲；1，使用中 ")
    private Integer status;
    @ApiModelProperty("上线状态;上线状态: 1: 上线，0:下线  ")
    private Integer  lineStatus; /** 上线状态;上线状态: 1: 上线，2:下线 */
    @ApiModelProperty("锁电量")
    private int betty;
    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String plate;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("降锁时间")
    private Date downTime;
    @ApiModelProperty("进场时间")
    private Date approachTime;
    @ApiModelProperty("预约时间")
    private Date startTime;
    @ApiModelProperty("订单编号")
    private String orderNo;
    @ApiModelProperty("预约时长")
    private String startDate;
    @ApiModelProperty("异常原因")
    private String excName;
    @ApiModelProperty("异常原因Id")
    private Long excCode;
    @ApiModelProperty("复位状态  true可以复位 false不可以")
    private boolean resetStatus = true;
    @ApiModelProperty("故障原因Id")
    private Long faultId;
    @ApiModelProperty("地磁车状态 0无车  1有车  2其他")
    private Integer carStatus = 2;
    @ApiModelProperty("故障原因名称")
    private String faultName;
    @ApiModelProperty("订单类型")
    private String orderType;
    @ApiModelProperty(value = "订单状态 状态：1，未支付；2，已支付；3，已完成；4，已取消 5 退款，6挂起订单，7关闭订单")
    private Short orderStatus;
    @ApiModelProperty(value = "订单id")
    private Long orderId;
    @ApiModelProperty(value = "floor")
    private String floor;
}
