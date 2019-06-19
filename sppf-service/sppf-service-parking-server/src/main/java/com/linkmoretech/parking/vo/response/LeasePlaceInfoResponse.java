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
 * Description: 查看长租详情
 * @date: 15:07 2019-06-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasePlaceInfoResponse {

    private String parkName;

    private String cityName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate ;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate ;

    @JsonProperty(value = "licensePlates")
    private List<String> licensePlateNoList;

    private String placeNo;

    private String linkMobile ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String createBy;

    private String updateBy;
}
