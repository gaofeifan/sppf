package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: alec
 * Description: 编辑长租车位回显数据
 * @date: 15:07 2019-06-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasePlaceEditResponse {

    private String leaseCode;

    private List<String> cityCodes;

    private Long parkId;

    private String linkMobile;

    private Date startDate;

    private Date endDate;

    @JsonProperty(value = "placeIds")
    private List<LeasePlaceResponse> placeIdList;

    @JsonProperty(value = "licensePlates")
    private List<String> licensePlateList;
}
