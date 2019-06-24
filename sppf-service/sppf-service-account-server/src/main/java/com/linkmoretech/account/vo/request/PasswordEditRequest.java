package com.linkmoretech.account.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: alec
 * Description:
 * @date: 10:26 2019-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordEditRequest {

    @NotEmpty(message = "密码不能为空")
    private String password;

    private String oldPassword;

    @NotNull(message = "重置方式不能为空")
    private Integer type;
}
