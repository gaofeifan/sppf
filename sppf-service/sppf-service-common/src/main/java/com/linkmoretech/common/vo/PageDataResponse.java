package com.linkmoretech.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页显示数据格式
 * @Author: alec
 * @Description:
 * @date: 下午3:58 2019/4/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDataResponse<T> {

    @ApiModelProperty(value = "总条数")
    private Long total;

    @ApiModelProperty(value = "数据列表")
    private List<T> data;

}
