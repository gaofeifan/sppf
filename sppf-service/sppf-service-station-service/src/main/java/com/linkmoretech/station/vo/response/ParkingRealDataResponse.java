package com.linkmoretech.station.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 16:01 2019-07-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingRealDataResponse {

    private String name;

    private String code;

    private Integer placeTotal;

    private Integer fixPlaceTotal;

    private Long  nowTempTotal;

    private Long nowFixTotal;

}
