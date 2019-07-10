package com.linkmoretech.versatile.vo.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: jhb
 * @Description:
 * @date: 8:33 PM 2019/4/29
 */
@Data
public class BaseDictGroupEditRequest {
	
	@ApiModelProperty(value = "字典分类主键ID", required = true)
    @NotNull(message = "分类主键ID不能为空")
	private Long id;
	
	@ApiModelProperty(value = "分类名称", required = true)
    @NotNull(message = "分类名称不能为空")
    private String name;
	
	@ApiModelProperty(value = "分类编码", required = true)
    @NotNull(message = "分类编码不能为空")
    private String code;
    
	@ApiModelProperty(value = "分类排序", required = true)
    @NotNull(message = "分类排序不能为空")
    private int orderIndex;
}
