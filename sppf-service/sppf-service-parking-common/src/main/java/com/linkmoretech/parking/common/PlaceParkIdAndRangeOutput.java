package com.linkmoretech.parking.common;

import lombok.Data;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/6/25
 */
@Data
public class PlaceParkIdAndRangeOutput {
    private Long parkId;

    private String parkName;

    private Long placeId;

    private String placeNo;
}
