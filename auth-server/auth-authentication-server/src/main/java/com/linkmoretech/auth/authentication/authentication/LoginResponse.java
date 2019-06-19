package com.linkmoretech.auth.authentication.authentication;

import lombok.Data;

/**
 * @Author: alec
 * Description:
 * @date: 13:53 2019-06-13
 */
@Data
public class LoginResponse {

    private Integer code;

    private String message;

    private Object data;

}
