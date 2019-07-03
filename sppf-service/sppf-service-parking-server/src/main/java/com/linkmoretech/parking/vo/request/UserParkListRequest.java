package com.linkmoretech.parking.vo.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("个人版车场列表")
@Data
public class UserParkListRequest {


	@ApiModelProperty(value = "城市ID", required = false)
	private Long cityId;
	@ApiModelProperty(value = "经度", required = true) 
//	@Range(min=73, max=137,message="经度请确保在中国范围内")
	private String longitude; 
	
	@ApiModelProperty(value = "纬度", required = true) 
//	@Range(min=3, max=54,message="纬度请确保在中国范围内")
	private String latitude;
	
	@ApiModelProperty(value = "城市标识", required = true) 
	private String cityFlag = "0";
	
	@ApiModelProperty(value = "首页标识1首页、0非首页", required = false) 
	private String homeFlag = "0";
	
	@ApiModelProperty(value = "车区名称（模糊搜索）cityFlag=0 表示所有车区搜索，车区名称默认为空", required = true) 
	private String preName;
	
}
