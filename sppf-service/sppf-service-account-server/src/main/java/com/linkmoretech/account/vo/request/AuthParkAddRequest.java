package com.linkmoretech.account.vo.request;

import lombok.Data;

/**
 * @Author: alec
 * Description:
 * @date: 16:36 2019-06-24
 */
@Data
public class AuthParkAddRequest {

    private Long parkId;

    private Long userId;

    private Integer authStatus;
}
