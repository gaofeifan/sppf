package com.linkmoretech.versatile.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 17:37 2019-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargeListResponse {

    @JsonProperty(value = "code")
    private String parkCode;

    @JsonProperty(value = "name")
    private String parkName;
}
