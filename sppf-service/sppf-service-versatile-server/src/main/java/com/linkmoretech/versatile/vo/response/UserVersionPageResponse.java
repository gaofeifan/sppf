package com.linkmoretech.versatile.vo.response;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jhb
 * @Description:
 * @date: 10:20 AM 2019/4/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户版本分页响应数据")
public class UserVersionPageResponse {
	
	@ApiModelProperty(value = "用户ID", required = false)
	private Long userId;
	@ApiModelProperty(value = "机型1.安卓2.ios", required = false)
	
    private Short client;
	@ApiModelProperty(value = "手机型号", required = false)
    private String model;
	
	@ApiModelProperty(value = "用户名", required = false)
    private String username;
    
	@ApiModelProperty(value = "系统版本号", required = false)
    private String osVersion;

	@ApiModelProperty(value = "uuid", required = false)
    private String uuid;
    
    @ApiModelProperty(value = "app版本号", required = false)
    private String version;
    
    @ApiModelProperty(value = "提交时间", required = false)
    private Date commitTime;
    
    @ApiModelProperty(value = "标识1个人版3管理版", required = false)
    private Integer system;
}
