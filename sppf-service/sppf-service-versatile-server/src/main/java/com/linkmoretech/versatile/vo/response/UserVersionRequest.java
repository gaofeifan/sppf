package com.linkmoretech.versatile.vo.response;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户版本上报")
public class UserVersionRequest {

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
