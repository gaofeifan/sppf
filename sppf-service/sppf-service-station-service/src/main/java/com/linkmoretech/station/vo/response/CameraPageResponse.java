package com.linkmoretech.station.vo.response;

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
@ApiModel("摄像头页面参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraPageResponse {
	
	@ApiModelProperty(value="主键")
	private Long id;
	
	@ApiModelProperty(value="车场Id")
    private Long parkId;
	
	@ApiModelProperty(value="车场名称")
    private String parkName;
    
	@ApiModelProperty(value="出入口位置类型 1出口 0入口")
    private Short positionType;
	
	@ApiModelProperty(value="出入口位置")
    private String position;
	
	@ApiModelProperty(value="状态 1启用 0禁用")
    private Short status;
    
	@ApiModelProperty(value="在线状态 1在线 0离线")
    private Short onlineStatus;
    
	@ApiModelProperty(value="设备序列号")
    private String serialNumber;
}
