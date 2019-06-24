package com.linkmoretech.parking.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 长租批量导入长租信息
 * @Author: alec
 * Description:
 * @date: 11:36 2019-05-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasePlaceBatchRequest {

    private Long parkId;

    private String username;

    @JsonProperty(value = "params")
    private List<LeasePlaceBatchParamsRequest> leasePlaceBatchParamsRequests;
}
