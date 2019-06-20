package com.linkmoretech.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("已结订单")
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
	
	/*public void copy(ResUserOrder ruo) {
		SimpleDateFormat sdf = new SimpleDateFormat("Y年M月d日 HH:mm");
		this.setOrderTime(sdf.format(ruo.getCreateTime()));
		this.setId(ruo.getId());
		Date start = ruo.getCreateTime();
		Date end = ruo.getEndTime();
		long day = 0;
		long hour = 0;
		long min = 0;
		long time = (end.getTime()-start.getTime())/(60*1000L);
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
		this.setStatus(ruo.getStatus().shortValue());
		this.setStallName(ruo.getStallName());
		this.setPrefectureName(ruo.getPreName()); 
		DecimalFormat df2 =new DecimalFormat("0.00");
		this.setTotalAmount(df2.format(ruo.getTotalAmount().doubleValue()));
	}*/
}
