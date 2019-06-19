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
 * @date: 18:43 2019-05-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasePlaceCreateRequest {

    private Long parkId;

    private String linkMobile;

    private Date startDate;

    private Date endDate;

    private String username;

    @JsonProperty(value = "placeIds")
    private List<Long> placeIdList;

    @JsonProperty(value = "licensePlates")
    private List<String> licensePlateList;
}
