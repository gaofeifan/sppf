package com.linkmoretech.user.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: alec
 * Description: 长租车位列表显示
 * @date: 16:36 2019-05-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaseUserListResponse {

    private String userId;

    private String linkMobile;

    private String parkName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private Integer status;

    private List<LeasePlaceInfoResponse> leasePlaceInfoResponses;

}
