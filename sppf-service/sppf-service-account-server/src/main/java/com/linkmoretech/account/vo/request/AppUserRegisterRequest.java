package com.linkmoretech.account.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 14:56 2019-06-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRegisterRequest {

    private String mobile;

    private Integer userSource;

    private String validateCode;
}
