package com.linkmoretech.parking.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 17:27 2019-06-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasePlaceResponse {

    private Long id;

    private String placeNo;
}
