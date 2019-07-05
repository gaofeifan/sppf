package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/6/13
 */
@Data
@Api("车位列表")
public class CarPalceListResponse {

    @ApiModelProperty("车位id")
    @JsonProperty("stallId")
    private Long id;
    @ApiModelProperty("车位类型 2临停 1固定 ")
    private Integer type;
    @ApiModelProperty("车位名称 ")
    @JsonProperty("stallName")
    private String carPalceName;
    /** 车位状态;车位状态: 1:空闲，2:使用 */
    @ApiModelProperty(value="车位状态 状态:0，空闲；1，使用中；4，下线  ")
    @JsonProperty("status")
    private Integer placeStatus;
    /** 地锁状态;地锁状态: 1:升起,2:降下, 3:故障 */
    @ApiModelProperty(value="锁状态 1，升起；2，降下  3故障")
    private Integer lockStatus;
    @ApiModelProperty(value="车位锁异常状态  true 正常车位  false 异常车位")
    private boolean excStatus = true;
    @ApiModelProperty(value="锁编号")

    private String lockSn;
}
