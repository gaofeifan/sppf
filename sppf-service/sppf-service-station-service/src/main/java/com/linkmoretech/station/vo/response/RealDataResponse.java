package com.linkmoretech.station.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 15:23 2019-07-15
 */
@Data
public class RealDataResponse {

    private Integer placeTotal;

    private Integer fixPlaceTotal;

    private Long  nowTempTotal;

    private Long nowFixTotal;

    @JsonProperty(value = "parking")
    List<ParkingRealDataResponse> parkingRealDataResponseList;

    public RealDataResponse() {
        this.placeTotal = 0;
        this.fixPlaceTotal = 0;
        this.nowFixTotal = 0L;
        this.nowTempTotal = 0L;
    }
}
