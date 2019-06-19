package com.linkmoretech.parking.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 11:43 2019-05-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasePlaceBatchParamsRequest {

    private Date startDate;

    private Date endDate;

    private String linkMobile;

    private List<String> placeList;

    private List<String> licensePlateList;
}
