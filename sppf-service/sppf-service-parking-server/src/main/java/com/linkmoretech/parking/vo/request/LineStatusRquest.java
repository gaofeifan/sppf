package com.linkmoretech.parking.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("车位上下线")
public class LineStatusRquest {

	@ApiModelProperty(value="车位id")
	private Long carPlaceId;
	
	@ApiModelProperty(value="车位上下线状态 0下线 1上线")
	private Integer state;
	
	@ApiModelProperty(value="原因id")
	private Long causeId;
	
	@ApiModelProperty(value="原因内容")
	private String causeName;
}
