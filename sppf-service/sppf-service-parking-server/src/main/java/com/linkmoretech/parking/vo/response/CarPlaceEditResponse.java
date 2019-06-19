package com.linkmoretech.parking.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 15:35 2019-05-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarPlaceEditResponse {

    private Long id;

    private String placeNo;

    private String lockCode;
}
