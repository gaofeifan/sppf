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
public class BaseDictCreateRequest {
	
	//名称
	@ApiModelProperty(value = "字典名称", required = true)
    @NotNull(message = "字典名称不能为空")
    private String name;
	
	//编码
	@ApiModelProperty(value = "字典编码", required = true)
    @NotNull(message = "字典编码不能为空")
    private String code;
    
    //分类id
	@ApiModelProperty(value = "分类ID", required = true)
    @NotNull(message = "分类ID不能为空")
    private Long groupId;
	
	//排序
	@ApiModelProperty(value = "字典排序", required = true)
    @NotNull(message = "字典排序不能为空")
    private int orderIndex;
	
	@ApiModelProperty(value = "其他", required = true)
    @NotNull(message = "其他不能为空")
    private int extra;
}
