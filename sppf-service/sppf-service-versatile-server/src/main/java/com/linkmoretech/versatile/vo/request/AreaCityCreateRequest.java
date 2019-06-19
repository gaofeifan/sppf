package com.linkmoretech.versatile.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: alec
 * @Description:
 * @date: 8:33 PM 2019/4/29
 */
@Data
public class AreaCityCreateRequest {

    @JsonProperty(value = "code")
    private String cityCode;

    @JsonProperty(value = "name")
    private String cityName;

    private Long parentId;
}
