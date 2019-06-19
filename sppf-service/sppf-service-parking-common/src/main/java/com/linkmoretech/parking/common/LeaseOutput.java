package com.linkmoretech.parking.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: alec
 * Description: 车牌号
 * @date: 13:30 2019-05-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaseOutput {

    private String leaseCode;

    private Long parkId;

    private String parkName;

    private Date startDate;

    private Date endDate;

    private Integer leaseStatus;

    private List<LeasePlaceOutput> placeNoList;
}
