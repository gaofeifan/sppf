package com.linkmoretech.order.vo;

import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单详情")
public class ResOrderDetail{
	@ApiModelProperty(value = "订单ID主键")
	private String orderId;
	@ApiModelProperty(value = "编号")
	private String orderNo;
	@ApiModelProperty(value = "车场名称")
	private String parkName;
	@ApiModelProperty(value = "车牌号")
	private String plateNo;
	@ApiModelProperty(value = "车位名")
	private String placeName;
	@ApiModelProperty(value = "状态")
	private Short status;
	/*@ApiModelProperty(value = "预约时间")
	private Date createTime;*/
	@ApiModelProperty(value = "开始时间")
	private Date createTime;
	@ApiModelProperty(value = "结束时间")
	private Date finishTime;
	@ApiModelProperty(value = "支付时间")
	private Date payTime;
	@ApiModelProperty(value = "支付类型[1免费，2停车券，3账户，4支付宝，5微信，6ApplePay、7微信、8银联云闪付、9华为Pay、10小米Pay、11建行龙支付]")
	private Short payType;
	@ApiModelProperty(value = "总金额")
	private BigDecimal totalAmount;
	@ApiModelProperty(value = "总金额[字符串]")
	private String stotalAmount;
	
	/*@ApiModelProperty(value = "停车券金额")
	private BigDecimal couponAmount;
	@ApiModelProperty(value = "停车券金额[字符串]")
	private String scouponAmount;*/
	
	@ApiModelProperty(value = "支付金额")
	private BigDecimal payAmount;
	
	@ApiModelProperty(value = "支付金额[字符串]")
	private String spayAmount;
	
	@ApiModelProperty(value = "停车时长")
	private String parkingTime;
	
	@ApiModelProperty(value = "离开免费时长")
	private Integer leaveTime;
	 
	public BigDecimal getTotalAmount() {
		if(this.totalAmount==null) {
			this.totalAmount = new BigDecimal(0d);
		}
		return this.totalAmount.setScale(2,   BigDecimal.ROUND_HALF_UP);
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public BigDecimal getPayAmount() {
		if(this.payAmount==null) {
			this.payAmount = new BigDecimal(0d);
		}
		return this.payAmount.setScale(2,   BigDecimal.ROUND_HALF_UP);
	}
	
	public String getStotalAmount() {
		if(this.totalAmount==null) {
			this.totalAmount = new BigDecimal(0d);
		}
		return this.totalAmount.setScale(2,   BigDecimal.ROUND_HALF_UP).toString();
	}

	
	public String getSpayAmount() {
		if(this.payAmount==null) {
			this.payAmount = new BigDecimal(0d);
		}
		return this.payAmount.setScale(2,   BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/*public void copy(ResUserOrder ruo) {  
		this.setOrderNo(ruo.getOrderNo());
		this.setPlateNumber(ruo.getPlateNo()); 
		this.setOrderTime(ruo.getCreateTime());
		this.setId(ruo.getId());
		this.setStartTime(ruo.getBeginTime()); 
		this.setPayTime(ruo.getUpdateTime());
		this.setEndTime(ruo.getEndTime());
		this.setStatus(ruo.getStatus().shortValue());
		this.setStallName(ruo.getStallName());
		this.setPrefectureName(ruo.getPreName());   
		
		if(ruo.getTotalAmount() == null) {
			ruo.setTotalAmount(new BigDecimal(0d));
		}
		if(ruo.getActualAmount() == null) {
			ruo.setActualAmount(new BigDecimal(0d));
		}
		if(ruo.getCouponAmount() == null) {
			ruo.setCouponAmount(new BigDecimal(0d));
		}
		
		this.setTotalAmount(ruo.getTotalAmount().setScale(2, RoundingMode.HALF_UP));
		this.setActualAmount(ruo.getActualAmount().setScale(2, RoundingMode.HALF_UP));
		this.setCouponAmount(ruo.getCouponAmount().setScale(2, RoundingMode.HALF_UP));
		
		this.setPayType(ruo.getPayType().shortValue());
		long day = 0;
		long hour = 0;
		long min = 0;
		long time = (this.getEndTime().getTime()-this.getStartTime().getTime())/(60*1000L);
		day = time / (24*60);
		hour =( time % (24*60) ) / 60;
		min = time % 60;
		StringBuffer parkingTime = new StringBuffer();
		if(day!=0) {
			parkingTime.append(day);
			parkingTime.append("天");
			parkingTime.append(hour);
			parkingTime.append("时"); 
		}else if(hour!=0) {
			parkingTime.append(hour);
			parkingTime.append("时");
		} 
		parkingTime.append(min);
		parkingTime.append("分");
		this.setParkingTime(parkingTime.toString()); 
	} */
}
