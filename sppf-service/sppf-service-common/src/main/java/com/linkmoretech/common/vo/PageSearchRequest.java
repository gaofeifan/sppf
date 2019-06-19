package com.linkmoretech.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 分页查询条件
 * @Author: alec
 * @Description:
 * @date: 下午4:51 2019/4/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageSearchRequest<T> {

    @NotNull
    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @NotNull
    @ApiModelProperty(value = "当前第几页")
    private Integer pageNo;

    @ApiModelProperty(value = "查询条件")
    private T condition;

}
