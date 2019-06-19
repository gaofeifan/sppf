package com.linkmoretech.parking.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description: 调用长租车位接口参数
 * @date: 11:48 2019-05-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasePlaceOutput {

    private Long placeId;

    private String placeNo;

    private String licensePlateNo;

    private Integer plateStatus;
}
