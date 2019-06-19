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
 * @date: 下午7:52 2019/4/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "创建订单参数")
public class OrderRequest {

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull
    private String userId;

    @ApiModelProperty(value = "车场ID", required = true)
    @NotNull
    private Long parkingId;

    @ApiModelProperty(value = "车位ID", required = true)
    @NotNull
    private String parkingLotId;

    @ApiModelProperty(value = "订单类型 1预约 2扫码 3 分享", required = true)
    @NotNull
    private Integer orderType;

    @ApiModelProperty(value = "车牌号ID", required = true)
    @NotNull
    private String plateId;

}
