package com.linkmoretech.versatile.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: jhb
 * @Description:
 * @date: 10:20 AM 2019/4/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户APP版本分页响应数据")
public class UserAppVersionPageResponse {
	
    @ApiModelProperty(value = "主键ID", required = false)
    private Long id;
    
    @ApiModelProperty(value = "版本号", required = true)
    private String version;

    @ApiModelProperty(value = "版本代号", required = true)
    private Long code;

    @ApiModelProperty(value = "名称", required = true)
    private String name;
    
    @ApiModelProperty(value = "是否有效", required = true)
    private Integer status = 0;

    @ApiModelProperty(value = "url", required = true)
    private String url;
    @ApiModelProperty(value = "适用客户端1安卓；2ios；", required = true)
    private Integer type;

    @ApiModelProperty(value = "必须升级1是；", required = true)
    private Integer updateStatus = 0;
    
    @ApiModelProperty(value = "创建时间", required = false)
    private Date createTime;
    
	@ApiModelProperty(value = "修改时间", required = false)
    private Date updateTime;
}
