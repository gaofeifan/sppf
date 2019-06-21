package com.linkmoretech.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 创建订单数据
 * @author jhb
 * @Date 2019年6月20日 下午7:26:06
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "创建订单数据")
public class OrderRequest {

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message="用户ID不能为空")
    private String userId;

    @ApiModelProperty(value = "车场ID", required = true)
    @NotNull(message="车场ID不能为空")
    private Long parkId;

    @ApiModelProperty(value = "车位ID", required = true)
    @NotNull(message="车位ID不能为空")
    private String placeId;

    @ApiModelProperty(value = "订单类型 1预约 2扫码 3 分享", required = true)
    @NotNull(message="订单类型不能为空")
    private Integer orderType;

    @ApiModelProperty(value = "车牌号ID", required = true)
    @NotNull(message="plateId不能为空")
    private String plateId;

}
