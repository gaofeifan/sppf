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
@ApiModel("当前订单信息")
public class ResCurrentOrder {
	@ApiModelProperty(value = "订单ID")
	private String orderId;
	
	@ApiModelProperty(value = "车场ID")
	private Long parkId;
	
	@ApiModelProperty(value = "车场名称")
	private String parkName;
	
	@ApiModelProperty(value = "车位ID")
	private Long placeId;
	
	@ApiModelProperty(value = "车位名称")
	private String placeName;
	/*
	@ApiModelProperty(value = "车位导航图")
	private String guideImage;
	@ApiModelProperty(value = "车位导航说明")
	private String guideRemark;
	@ApiModelProperty(value = "车区地址")
	private String prefectureAddress;
	*/
	
	@ApiModelProperty(value = "车区经度")
	private BigDecimal preLongitude;
	@ApiModelProperty(value = "车区纬度")
	private BigDecimal preLatitude; 
	
	@ApiModelProperty(value = "车区经度[高德、腾讯]")
	private double tencentLongitude;
	
	@ApiModelProperty(value = "车区纬度[高德、腾讯]")
	private double tencentLatitude; 
	
	
	@ApiModelProperty(value = "车牌号")
	private String plateNo;
	
	@ApiModelProperty(value = "预约时间")
	private Date createTime;
	
	@ApiModelProperty(value = "停车时长[分钟]")
	private Integer parkingTime;
	
	@ApiModelProperty(value = "结束时间")
	private Date finishTime;
	
	@ApiModelProperty(value = "停车费用")
	private BigDecimal totalAmount;
	
	@ApiModelProperty(value = "支付类型")
	private Short payType;
	
	@ApiModelProperty(value = "实际费用")
	private BigDecimal payAmount; 
	
	@ApiModelProperty(value = "订单状态[1预约中,3已结账,6已挂起]")
	private Short status;  
	
	@ApiModelProperty(value = "蓝牙地址串用")
	private String bluetooth;
	
	@ApiModelProperty(value = "金额[字符串]")
	private String amount;
	
	@ApiModelProperty(value = "订单状态[1取消预约,2结账离场]")
	private Short cancelFlag = 1;
	
	@ApiModelProperty(value = "免费时长")
	private int freeMins;
	
	@ApiModelProperty(value = "剩余时长")
	private int remainMins;
	
	@ApiModelProperty(value = "订单类型[1普通订单,2扫码降锁订单]")
	private Short orderSource = 1;
	
	@ApiModelProperty(value = "降锁状态[0已预约,1已降锁,2已挂起去结账]")
	private Short downFlag = 0;
	
	@ApiModelProperty(value = "车位所在层")
	private String underLayer;
	
	@ApiModelProperty(value = "路径规划标识默认为0禁用 展示车位在哪、启用 为1展示最新的路径规划")
	private Short pathFlag = 0;
	
	public BigDecimal getTotalAmount() {
		if(this.totalAmount==null) {
			this.totalAmount = new BigDecimal(0d);
		}
		return this.totalAmount.setScale(2,   BigDecimal.ROUND_HALF_UP);
	}
	
	public String getAmount() {
		if(this.totalAmount==null) {
			this.totalAmount = new BigDecimal(0d);
		}
		return this.totalAmount.setScale(2,   BigDecimal.ROUND_HALF_UP).toString();
	}
	
	public BigDecimal getPayAmount() {
		if(this.payAmount==null) {
			this.payAmount = new BigDecimal(0d);
		}
		return this.payAmount.setScale(2,   BigDecimal.ROUND_HALF_UP);
	}
	
	public BigDecimal getPreLongitude() {
		return preLongitude;
	}
	public void setPreLongitude(BigDecimal preLongitude) {
		this.preLongitude = preLongitude;
	}
	public BigDecimal getPreLatitude() {
		return preLatitude;
	}
	public void setPreLatitude(BigDecimal preLatitude) {
		this.preLatitude = preLatitude;
	}
	private final static double PI = 3.14159265358979324;
	
	public double getTencentLongitude() { 
		double tx_lon; 
		double x = this.getPreLongitude().doubleValue() - 0.0065, y = this.getPreLatitude().doubleValue() - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
		tx_lon = z * Math.cos(theta); 
		return tx_lon; 
	}
	
	public double  getTencentLatitude() {
		double tx_lat;  
		double x = this.getPreLongitude().doubleValue() - 0.0065, y = this.getPreLatitude().doubleValue() - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI); 
		tx_lat = z * Math.sin(theta); 
		return tx_lat;
	}
	 
	public void setTencentLongitude(double tencentLongitude) {
		this.tencentLongitude = tencentLongitude;
	}
	 
	public void setTencentLatitude(double tencentLatitude) {
		this.tencentLatitude = tencentLatitude;
	}
	/*@JsonIgnore
	public void copy(ResUserOrder ruo) {
		this.setStartTime(ruo.getCreateTime());
		this.setId(ruo.getId());
		Date start = ruo.getCreateTime();
		Date end = new Date(); 
		if(ruo.getStatus()==OrderStatus.SUSPENDED.value) {
			end = ruo.getStatusTime(); 
		}
		this.setEndTime(end);
		this.setParkingTime(new Long((end.getTime()-start.getTime())/(60*1000L)).intValue());
		this.setPlateNumber(ruo.getPlateNo());
		this.setEntId(ruo.getEntId());
		this.setPrefectureId(ruo.getPreId());
		this.setStallId(ruo.getStallId());
		this.setStatus(ruo.getStatus().shortValue());
		this.setStallName(ruo.getStallName());
		this.setPrefectureName(ruo.getPreName());  
		System.out.println("当前订单金额 ={}" + ruo.getTotalAmount() + "保留2位小数后" +  ruo.getTotalAmount().setScale(2, RoundingMode.HALF_UP));
		if(ruo.getTotalAmount() != null) {
			this.setTotalAmount(ruo.getTotalAmount().setScale(2, RoundingMode.HALF_UP));
		}
		this.setOrderSource(ruo.getOrderSource());
	}*/
	public String getBluetooth() {
		return bluetooth;
	}
	public void setBluetooth(String bluetooth) {
		this.bluetooth = bluetooth;
	}
	public Short getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(Short cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public int getFreeMins() {
		return freeMins;
	}
	public void setFreeMins(int freeMins) {
		this.freeMins = freeMins;
	}
	public int getRemainMins() {
		return remainMins;
	}
	public void setRemainMins(int remainMins) {
		this.remainMins = remainMins;
	}
	public Short getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(Short orderSource) {
		this.orderSource = orderSource;
	}
	public Short getDownFlag() {
		return downFlag;
	}
	public void setDownFlag(Short downFlag) {
		this.downFlag = downFlag;
	}
	
}
