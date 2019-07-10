package com.linkmoretech.versatile.vo.response;

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
@ApiModel("数据字典响应数据")
public class BaseDictResponse {
	
	@ApiModelProperty(value="主键")
	private Long id;
	@ApiModelProperty(value="分组id")
    private Long groupId;
	@ApiModelProperty(value="名称")
    private String name;
	@ApiModelProperty(value="编码")
    private String code;
	@ApiModelProperty(value="排序")
    private int orderIndex;
	@ApiModelProperty(value="备注")
    private int extra;
}
