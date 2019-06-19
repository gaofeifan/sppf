package com.linkmoretech.parking.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 18:04 2019-06-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasePlaceEditRequest {

    private String leaseCode;

    private String linkMobile;

    private Date startDate;

    private Date endDate;

    private String username;

    @JsonProperty(value = "placeIds")
    private List<Long> placeIdList;

    @JsonProperty(value = "licensePlates")
    private List<String> licensePlateList;
}
