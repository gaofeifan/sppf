package com.linkmoretech.parking.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 15:43 2019-05-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPlaceEditRequest {

    private Long id;

    private String placeNo;

    private String lockCode;

    private String username;
}
