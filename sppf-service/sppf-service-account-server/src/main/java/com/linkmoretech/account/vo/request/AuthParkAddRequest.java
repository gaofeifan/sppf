package com.linkmoretech.account.vo.request;

import lombok.Data;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 16:36 2019-06-24
 */
@Data
public class AuthParkAddRequest {

    private List<Long> parkIdList;

    private Long userId;

    private Integer authStatus;
}
