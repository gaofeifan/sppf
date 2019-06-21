package com.linkmoretech.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;

/**
 * @Author: jhb
 * @Description:
 * @date: 下午8:15 2019/6/10
 */
/**
 * 降锁请求数据
 * @author jhb
 * @Date 2019年6月20日 下午7:24:39
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "降锁请求")
public class OrderDownLockRequest {

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message="用户ID不能为空")
    private String userId;

    @ApiModelProperty(value = "订单ID", required = true)
    @NotNull(message="订单ID不能为空")
    private String orderId;

    @ApiModelProperty(value = "车位ID", required = true)
    @NotNull(message="车位ID不能为空")
    private String placeId;

    @ApiModelProperty(value = "到达状态[0未知，1已到达，2未到达]", required = true)
    @Range(min=0, max=3,message="到达状态只能为0，1，2的数")
    private Short parkingStatus;

}
