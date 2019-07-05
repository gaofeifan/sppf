package com.linkmoretech.versatile.vo.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("上报版本")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffAppVersionRequest {

	@ApiModelProperty(value="客户端",required=true)
	@NotNull(message="客户端不能为空")
	private Short client;

	@ApiModelProperty(value="型号",required=true)
	@NotNull(message="型号不能为空")
	private String model;

	@ApiModelProperty(value="os版本",required=true)
	@NotNull(message="os版本不能为空")
	private String osVersion; 

	@ApiModelProperty(value="版本",required=true)
	@NotNull(message="版本不能为空")
	private String version;
}
