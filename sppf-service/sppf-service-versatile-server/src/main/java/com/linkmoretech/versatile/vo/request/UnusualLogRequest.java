package com.linkmoretech.versatile.vo.request;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author jhb
 * @Date 2019年7月11日 下午3:15:09
 * @Version 1.0
 */
@ApiModel("异常日志上报请求参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnusualLogRequest {

	@ApiModelProperty(value="主键 非必填",required=false)
	private Long id;

	@ApiModelProperty(value="app版本 必填",required=true)
	@NotNull(message="app版本不能为空") 
	private String appVersion;

	@ApiModelProperty(value="os版本（系统版本号） 必填",required=true)
	@NotNull(message="os版本（系统版本号）不能为空") 
	private String osVersion;

	@ApiModelProperty(value="型号 必填",required=true)
	@NotNull(message="型号不能为空") 
	private String model;

	@ApiModelProperty(value="客户端类型0:微信小程序,1:android,2:ios 必填 ",required=true)
	@NotNull(message="客户端类型不能为空") 
	@Range(min=0,max=2,message="客户端类型有误")
	private Integer clientType;

	@ApiModelProperty(value="日志级别",required=false)
	private String level;

	@ApiModelProperty(value="品牌 必填",required=true)
	@NotNull(message="品牌不能为空") 
	private String brand;
	
	@ApiModelProperty(value="内容 必填",required=true)
	@NotNull(message="内容不能为空") 
	private String content;

	@ApiModelProperty(value="1 个人版 2 物业版 3管理版",required=true)
	@NotNull(message="类型不能为空") 
	private Integer system;
}
