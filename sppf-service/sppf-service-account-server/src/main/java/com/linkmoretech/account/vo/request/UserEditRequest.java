package com.linkmoretech.account.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alec
 * Description: 编辑用户信息
 * @date: 18:14 2019-06-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequest {

    private Long id;

    private String mobile;

    private String password;

    private List<Long> roles;

}
