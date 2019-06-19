package com.linkmoretech.parking.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: alec
 * @Description:
 * @date: 1:38 PM 2019/4/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "车位分布")
public class FloorPlanRequest {

    @ApiModelProperty(value = "车场名称", required = true)
    @NotEmpty(message = "车位分布对应名称不能为空")
    @JsonProperty(value = "name")
    private String floorName;

    @ApiModelProperty(value = "该区域车位数", required = true)
    @NotNull(message = "车位分布对应车位数不能空位")
    private Integer placeNumber;
}
