package com.linkmoretech.account.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 15:50 2019-06-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String username;

    private String password;

    private String clientId;
}
