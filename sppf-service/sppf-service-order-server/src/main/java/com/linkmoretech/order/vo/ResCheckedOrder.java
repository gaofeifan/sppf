package com.linkmoretech.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("已完成订单")
public class ResCheckedOrder {
	@ApiModelProperty(value = "订单主键")
	private String orderId;
	@ApiModelProperty(value = "车场名称")
	private String parkName;
	@ApiModelProperty(value = "车位名称")
	private String placeName;
	@ApiModelProperty(value = "状态[3已完成,4已取消,7已关闭]")
	private Short status;
	@ApiModelProperty(value = "创建时间")
	private String createTime; 
	@ApiModelProperty(value = "停车时长")
	private String parkingTime;
	@ApiModelProperty(value = "停车金额")
	private String totalAmount;
	
}
