package com.linkmoretech.user.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 当前版本
 * @author jhb
 * @Date 2019年6月27日 下午1:41:36
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("当前版本响应")
public class UserAppVersionResponse implements Serializable{
	/**
	 * serial id
	 */
	private static final long serialVersionUID = -2937247643771685452L;

	@ApiModelProperty(value="app版本号")
	private String version;

	@ApiModelProperty(value="版本代号")
    private Long versionCode;

	@ApiModelProperty(value="版本名")
    private String versionName; 

	@ApiModelProperty(value="适用客户端：1安卓；2ios；")
    private String downloadUrl; 
    
	@ApiModelProperty(value="是否必须升级；1是")
    private Integer mustUpdate; 

	@ApiModelProperty(value="详情")
    private String description;
    
}
