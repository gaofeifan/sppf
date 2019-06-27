package com.linkmoretech.parking.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: GFF
 * @Description: 查询车位
 * @Date: 2019/6/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceParkIdAndRangeInput {

    /**
     *  车场id
     */
    private Long parkId;

    /**
     *  区间开始的id  要求与endId长度一致
     */
    private List<Long> headId = new ArrayList<>();

    /**
     *  区间结束id    要求与headId长度一致
     */
    private List<Long> endId = new ArrayList<>();




}
