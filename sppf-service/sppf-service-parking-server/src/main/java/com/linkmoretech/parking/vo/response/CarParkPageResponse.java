package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页显示车场数据VO
 * @Author: alec
 * @Description:
 * @date: 1:53 PM 2019/4/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarParkPageResponse {

    private Long id;

    @JsonProperty(value = "name")
    private String parkName;

    private Integer placeNumber;

    private Integer lockNumber;

    @JsonProperty(value = "type")
    private Integer parkType ;

    private String chargeName;

    private Integer fixedStatus;

    private Integer tempStatus;

    @JsonProperty(value = "floorPlans")
    private List<FloorPlanResponse> floorPlanResponseList;
}
