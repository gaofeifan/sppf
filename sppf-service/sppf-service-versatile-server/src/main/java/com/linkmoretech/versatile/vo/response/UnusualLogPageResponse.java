package com.linkmoretech.versatile.vo.response;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 异常日志分页响应数据
 * @author jhb
 * @Date 2019年7月11日 下午3:24:51
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "异常日志分页响应数据")
public class UnusualLogPageResponse {
	
	@ApiModelProperty(value="主键 非必填")
	private Long id;

	@ApiModelProperty(value="app版本 必填")
	private String appVersion;

	@ApiModelProperty(value="os版本（系统版本号） 必填")
	private String osVersion;

	@ApiModelProperty(value="型号 必填")
	private String model;

	@ApiModelProperty(value="客户端类型0:微信小程序,1:android,2:ios 必填 ")
	private Integer clientType;

	@ApiModelProperty(value="日志级别")
	private String level;

	@ApiModelProperty(value="品牌 必填")
	private String brand;
	
	@ApiModelProperty(value="内容 必填")
	private String content;

	@ApiModelProperty(value="1 个人版 2 物业版 3管理版")
	@NotNull(message="类型不能为空") 
	private Integer system;
}
