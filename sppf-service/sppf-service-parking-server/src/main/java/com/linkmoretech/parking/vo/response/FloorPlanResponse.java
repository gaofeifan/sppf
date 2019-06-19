package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * @Description:
 * @date: 1:54 PM 2019/4/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorPlanResponse {

    @ApiModelProperty(value = "车位分布ID")
    private Long id;

    @ApiModelProperty(value = "车位分布名称")
    @JsonProperty(value = "name")
    private String floorName;

    @ApiModelProperty(value = "车位数")
    private Integer placeNumber;
}
