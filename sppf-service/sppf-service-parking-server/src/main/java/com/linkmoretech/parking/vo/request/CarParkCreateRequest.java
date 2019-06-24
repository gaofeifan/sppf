package com.linkmoretech.parking.vo.request;

/**
 * @Author: alec
 * @Description:
 * @date: 1:26 PM 2019/4/29
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 创建车场入参
 * @Author: alec
 * @Description:
 * @date: 下午4:13 2019/4/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "添加车场参数")
public class CarParkCreateRequest {

    @ApiModelProperty(value = "车场名称", required = true)
    @NotEmpty(message = "车场名称不能为空")
    @JsonProperty(value = "name")
    private String parkName;

    @ApiModelProperty(value = "车场车位总数", required = true)
    @NotNull(message = "车场车位总数不能为空")
    private Integer placeNumber;

    @ApiModelProperty(value = "实装车位总数", required = true)
    @NotNull(message = "实装车位总数不能为空")
    private Integer lockNumber;

    @ApiModelProperty(value = "车场使用计费", required = true)
    @NotNull(message = "车场使用计费不能为空")
    private String chargeCode;

    @ApiModelProperty(value = "车场使用计费", required = true)
    @NotNull(message = "车场使用计费不能为空")
    private String chargeName;

    @ApiModelProperty(value = "城市编码", required = true)
    @NotEmpty(message = "城市编码不能为空")
    private String cityCode;

    @ApiModelProperty(value = "所在位置", required = true)
    @NotEmpty(message = "所在位置不能为空")
    private String location;

    @ApiModelProperty(value = "运营时间（开始）", required = true)
    @NotEmpty(message = "运营时间（开始）不能为空")
    @JsonProperty(value = "start")
    private String startTime;

    @ApiModelProperty(value = "运营时间（结束）", required = true)
    @NotEmpty(message = "运营时间（结束）不能为空")
    @JsonProperty(value = "end")
    private String endTime;

    @ApiModelProperty(value = "车位车位分布情况", required = true)
    @NotNull(message = "车位分布不能为空")
    @JsonProperty(value = "floorPlans")
    private List<FloorPlanRequest> floorPlanRequests;

    @ApiModelProperty(value = "高德地图坐标", required = true)
    @NotEmpty(message = "高德地图坐标不能为空")
    private String gaodeMap;

    @ApiModelProperty(value = "百度地图坐标", required = true)
    @NotEmpty(message = "百度地图坐标不能为空")
    private String baiduMap;

    private String username;
}
