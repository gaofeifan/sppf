package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.linkmoretech.parking.vo.request.FloorPlanRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 车场编辑
 * @Author: alec
 * @Description:
 * @date: 11:13 AM 2019/5/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarParkEditResponse {

    private Long id;

    @JsonProperty(value = "name")
    private String parkName;

    private Integer placeNumber;

    private Integer lockNumber;

    private String chargeCode;

    private List<String> cityCodes;

    private String location;

    @JsonProperty(value = "start")
    private String startTime;

    @JsonProperty(value = "end")
    private String endTime;

    @JsonProperty(value = "floorPlans")
    private List<FloorPlanResponse> floorPlanResponses;

    private String gaodeMap;

    private String baiduMap;
}
