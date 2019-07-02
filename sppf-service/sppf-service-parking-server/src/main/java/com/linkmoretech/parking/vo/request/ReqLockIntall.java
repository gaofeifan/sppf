package com.linkmoretech.parking.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;

@ApiModel("地锁安装")
@Data
public class ReqLockIntall {

	@ApiModelProperty("车区Id")
	Long preId;
	@ApiModelProperty("分区名称")
	String  areaName;
	@ApiModelProperty("车位号")
	String stallName;
	@ApiModelProperty("序列号")
	String lockSn;
	@ApiModelProperty("所在楼层")
	String floor;
	@ApiModelProperty("所在楼层Id")
	Long floorId;

	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public Long getPreId() {
		return preId;
	}
	public void setPreId(Long preId) {
		this.preId = preId;
	}
	

	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getLockSn() {
		if(lockSn != null) {
			if(lockSn.contains("0000")) {
				lockSn = lockSn.substring(4);
			}
			lockSn = lockSn.toUpperCase();
		}
		return lockSn;
	}
	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	
	
	
}
