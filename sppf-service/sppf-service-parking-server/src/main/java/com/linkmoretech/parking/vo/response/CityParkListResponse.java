package com.linkmoretech.parking.vo.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author GFF
 * @Description 城市车区列表
 * @Date 2019/6/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Api("城市车场列表")
public class CityParkListResponse {
    @ApiModelProperty("城市编码")
    private String cityCode;
    @ApiModelProperty("城市名称")
    private String cityName;
    @ApiModelProperty("城市id")
    private Long id;
    @ApiModelProperty("车场列表")
    private List<CarParkListResponse> prakList = new ArrayList<>();




}
