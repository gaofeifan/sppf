package com.linkmoretech.parking.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: alec
 * Description: 车位锁创建参数
 * @date: 10:07 2019-05-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarLockCreateRequest {

    @NotEmpty(message = "车位编号不能为空")
    private String placeNo ;

    @NotEmpty(message = "车锁序列号不能为空")
    private String lockCode ;
}
