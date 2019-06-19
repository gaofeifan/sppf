package com.linkmoretech.order.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * @Description:
 * @date: 下午8:00 2019/4/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "创建，编辑订单 返回信息")
public class OrderEditResponse {

    @ApiModelProperty(value = "用户账号")
    @JsonProperty(value = "orderId")
    private String orderId;

    @ApiModelProperty(value = "订单类型")
    @JsonProperty(value = "name")
    private Integer orderType;

    @ApiModelProperty(value = "订单状态")
    @JsonProperty(value = "name")
    private Integer orderStatus;
}
