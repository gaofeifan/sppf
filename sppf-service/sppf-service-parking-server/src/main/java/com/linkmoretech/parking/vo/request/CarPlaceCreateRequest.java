package com.linkmoretech.parking.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: alec
 * Description: 车位创建参数
 * @date: 10:02 2019-05-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPlaceCreateRequest {

    @NotNull(message = "车场ID不能为空")
    private Long carParkId;

    @NotNull(message = "车位分布ID不能为空")
    private Long floorPlanId;

    @JsonProperty(value = "locks")
    @NotNull(message = "车锁信息不能为空")
    private List<CarLockCreateRequest> carLockCreateRequestList;

    private String username;


}
