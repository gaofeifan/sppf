package com.linkmoretech.account.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description: 用户登录成功响应
 * @date: 15:32 2019-06-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessResponse {

    private String token;

    private String refreshToken;


}
