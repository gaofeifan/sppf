package com.linkmoretech.user.vo;

import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户指南
 * @author jhb
 * @Date 2019年6月27日 下午4:53:30
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户指南响应")
public class UserGuideResponse {
	
	@ApiModelProperty(value="主键")
	private Long id;
	@ApiModelProperty(value="标题")
	private String title;
	@ApiModelProperty(value="级别")
	private Integer level;
	@ApiModelProperty(value="类型")
	private Integer type;
	@ApiModelProperty(value="路径")
	private String url;
	@ApiModelProperty(value="子集合")
	private List<UserGuideResponse> children;
	
}
