package com.linkmoretech.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 操作订单数据
 * @author jhb
 * @Date 2019年6月20日 下午7:25:43
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "操作订单数据")
public class OrderOptionRequest {

    @ApiModelProperty(value = "订单ID", required = true)
    @NotNull
    private String orderId;

    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;
}
