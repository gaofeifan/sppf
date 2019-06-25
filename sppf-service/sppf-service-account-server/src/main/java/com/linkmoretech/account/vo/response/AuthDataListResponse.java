package com.linkmoretech.account.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 17:26 2019-06-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDataListResponse {

    private Long id;

    private Long parkId;

    private String parkName;

    private List<String> placeNo;
}
