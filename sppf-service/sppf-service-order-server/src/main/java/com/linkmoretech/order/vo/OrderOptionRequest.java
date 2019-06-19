package com.linkmoretech.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author: alec
 * @Description:
 * @date: 下午8:02 2019/4/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "操作订单参数")
public class OrderOptionRequest {

    @ApiModelProperty(value = "订单ID", required = true)
    @NotNull
    private String orderId;

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull
    private String userId;
}
