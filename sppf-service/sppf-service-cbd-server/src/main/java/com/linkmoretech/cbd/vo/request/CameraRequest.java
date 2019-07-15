package com.linkmoretech.cbd.vo.request;

import javax.validation.constraints.NotNull;
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
@ApiModel("摄像头请求参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraRequest {
	
	@ApiModelProperty(value="主键 非必填",required=false)
	private Long id;
	
	@ApiModelProperty(value="车场id",required=true)
	@NotNull(message="车场id不能为空") 
    private Long parkId;
    //
	@ApiModelProperty(value="车场名称",required=true)
	@NotNull(message="车场名称不能为空") 
    private String parkName;
    
	@ApiModelProperty(value="出入口位置类型 1出口 0入口",required=true)
	@NotNull(message="出入口位置类型不能为空") 
    private Short positionType;
	
	@ApiModelProperty(value="出入口位置",required=true)
	@NotNull(message="出入口位置不能为空") 
    private String position;
	
	@ApiModelProperty(value="状态 1启用 0禁用",required=false)
    private Short status = 0;
    
	@ApiModelProperty(value="在线状态 1在线 0离线",required=false)
    private Short onlineStatus;
    
	@ApiModelProperty(value="备注",required=false)
    private String remarks;
    
	@ApiModelProperty(value="设备序列号",required=true)
	@NotNull(message="设备序列号不能为空") 
    private String serialNumber;
}
