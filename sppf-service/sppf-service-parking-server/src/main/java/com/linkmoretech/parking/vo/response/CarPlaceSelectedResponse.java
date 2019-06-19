package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description: 车位下拉数据
 * @date: 10:19 2019-05-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPlaceSelectedResponse {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    private String placeNo;
}
