package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description: 车位分页数据显示
 * @date: 10:16 2019-05-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPlacePageResponse {

    private Long id;

    @JsonProperty(value = "name")
    private String parkName;

    private String placeNo;

    private Integer electric;

    private Integer lockStatus;

    private Integer placeStatus;

    @JsonProperty(value = "type")
    private Integer placeType;

    private String chargeName;

    @JsonProperty(value = "line")
    private Integer lineStatus;
}
