package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: alec
 * @Description:
 * @date: 6:07 PM 2019/5/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParkInfoResponse {

    @JsonProperty(value = "name")
    private String parkName;

    private Integer placeNumber;

    private Integer lockNumber;

    private String chargeName;

    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String createBy;

    private String updateBy;

    @JsonProperty(value = "floorPlans")
    private List<FloorPlanResponse> floorPlanResponseList;
}
