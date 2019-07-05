package com.linkmoretech.versatile.vo.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户APP版本请求数据")
public class UserAppVersionRequest {
	
    @ApiModelProperty(value = "主键ID", required = false)
    private Long id;
    
    @ApiModelProperty(value = "版本号", required = true)
    @NotNull(message = "版本号不能为空")
    private String version;

    @ApiModelProperty(value = "版本代号", required = true)
    @NotNull(message = "版本代号不能为空")
    private Long code;

    @ApiModelProperty(value = "名称", required = true)
    @NotNull(message = "名称不能为空")
    private String name;
    
    @ApiModelProperty(value = "是否有效", required = true)
    private Integer status = 0;

    @ApiModelProperty(value = "url", required = true)
    @NotNull(message = "url不能为空")
    private String url;
    @ApiModelProperty(value = "适用客户端", required = true)
    @NotNull(message = "1安卓；2ios；")
    private Integer type;

    @ApiModelProperty(value = "必须升级", required = true)
    @NotNull(message = "1是；")
    private Integer updateStatus = 0;

    @ApiModelProperty(value = "描述", required = true)
    @NotNull(message = "描述不能为空")
    private String description;

}