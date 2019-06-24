package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: alec
 * Description: 长租车位列表数据结构
 * @date: 17:02 2019-05-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasePlaceListResponse {

    private Long id;

    private String placeNo;

    private String parkName ;

    private String linkMobile ;

    @JsonProperty(value = "licensePlates")
    private List<String> licensePlateNoList;

    @JsonProperty(value = "status")
    private Integer leaseStatus ;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate ;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate ;
}
