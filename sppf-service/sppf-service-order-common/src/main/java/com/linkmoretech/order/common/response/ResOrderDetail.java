package com.linkmoretech.order.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单详情")
public class ResOrderDetail{
	@ApiModelProperty(value = "主键")
	private String id;
	@ApiModelProperty(value = "编号")
	private String orderNo;
	@ApiModelProperty(value = "专区名")
	private String prefectureName;
	@ApiModelProperty(value = "车牌号")
	private String plateNumber;
	@ApiModelProperty(value = "车位名")
	private String stallName;
	@ApiModelProperty(value = "状态")
	private Short status;
	@ApiModelProperty(value = "预约时间")
	private Date orderTime;
	@ApiModelProperty(value = "开始时间")
	private Date startTime;
	@ApiModelProperty(value = "结束时间")
	private Date endTime;
	@ApiModelProperty(value = "支付时间")
	private Date payTime;
	@ApiModelProperty(value = "支付类型[1免费，2停车券，3账户，4支付宝，5微信，6ApplePay、7微信、8银联云闪付、9华为Pay、10小米Pay、11建行龙支付]")
	private Short payType;
	@ApiModelProperty(value = "总金额")
	private BigDecimal totalAmount;
	@ApiModelProperty(value = "总金额[字符串]")
	private String stotalAmount;
	
	@ApiModelProperty(value = "停车券金额")
	private BigDecimal couponAmount;
	@ApiModelProperty(value = "停车券金额[字符串]")
	private String scouponAmount;
	
	@ApiModelProperty(value = "支付金额")
	private BigDecimal actualAmount;
	
	@ApiModelProperty(value = "支付金额[字符串]")
	private String sactualAmount;
	
	@ApiModelProperty(value = "停车时长")
	private String parkingTime;
	@ApiModelProperty(value = "离开免费时长")
	private Integer leaveTime;

}
