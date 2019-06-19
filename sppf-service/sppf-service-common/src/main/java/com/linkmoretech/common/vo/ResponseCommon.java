package com.linkmoretech.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定义统一响应码
 * @author Alec
 * @date 2019/4/4 14:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCommon<T> {

    @ApiModelProperty(value = "响应码")
    private Integer code;

    @ApiModelProperty(value = "服务器响应提示")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private T data;
}
