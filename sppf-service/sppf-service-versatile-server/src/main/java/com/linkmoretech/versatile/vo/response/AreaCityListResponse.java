package com.linkmoretech.versatile.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * @Description:
 * @date: 10:20 AM 2019/4/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaCityListResponse {

    private Long id;

    private String code;

    private String name;
}
