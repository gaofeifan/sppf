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
public class BaseDictEditRequest {
	
	@ApiModelProperty(value = "字典主键ID", required = true)
    @NotNull(message = "字典主键ID不能为空")
	private Long id;
	
	@ApiModelProperty(value = "字典名称", required = true)
    @NotNull(message = "字典名称不能为空")
    private String name;
	
	@ApiModelProperty(value = "字典编码", required = true)
    @NotNull(message = "字典编码不能为空")
    private String code;
    
	@ApiModelProperty(value = "分类ID", required = true)
    @NotNull(message = "分类ID不能为空")
    private Long groupId;
	
	@ApiModelProperty(value = "字典排序", required = true)
    @NotNull(message = "字典排序不能为空")
    private int orderIndex;
	
	@ApiModelProperty(value = "其他", required = true)
    @NotNull(message = "其他不能为空")
    private int extra;
}
