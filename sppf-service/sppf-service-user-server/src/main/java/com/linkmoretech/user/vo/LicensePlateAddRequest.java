package com.linkmoretech.user.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 添加车牌号入参
 * @Author: alec
 * @Description:
 * @date: 下午7:55 2019/4/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "添加车牌号入参")
public class LicensePlateAddRequest {

    @NotEmpty(message = "车牌号不能为空")
    @JsonProperty(value = "vehMark")
    @ApiModelProperty(value = "车牌号")
    private String plateNo;

    @NotEmpty(message = "账号不能为空")
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @NotNull(message = "车牌类型不能为空")
    @ApiModelProperty(value = "车牌类型 0 燃油 1 新能源")
    private Integer plateType;
}
