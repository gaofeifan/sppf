package com.linkmoretech.user.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 车牌号响应数据
 * @Author: alec
 * @Description:
 * @date: 下午4:49 2019/4/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "车牌响应信息")
public class LicensePlateResponse {

    @ApiModelProperty(value = "车牌号")
    @JsonProperty(value = "vehMark")
    private String plateNo;

    @ApiModelProperty(value = "车牌类型 0 燃油 1 新能源")
    @JsonProperty(value = "type")
    private Integer plateType;
}
