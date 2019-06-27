package com.linkmoretech.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 编辑用户参数
 * @Author: alec
 * @Description:
 * @date: 下午7:37 2019/4/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "编辑用户参数")
public class UserEditRequest {

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull
    private String id;

    @ApiModelProperty(value = "用户昵称")
    private String userNick;

    @ApiModelProperty(value = "用户性别")
    private Integer userSex;

    @ApiModelProperty(value = "车辆型号")
    private String vehicleType;

    @ApiModelProperty(value = "车辆品牌")
    private String vehicleBrand;
}
