package com.linkmoretech.parking.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description: 上下线车位参数
 * @date: 14:19 2019-05-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParkLineRequest {

    private Long id;

    private Integer lineStatus;

    private Integer lineType;

    private String username;
}
