package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 车场下拉数据
 * @Author: alec
 * @Description:
 * @date: 2:04 PM 2019/4/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarParkSelectResponse {

    private Long id;

    @JsonProperty(value = "name")
    private String parkName;

    @JsonProperty(value = "floorPlans")
    private List<FloorPlanResponse> floorPlanResponseList;
}
