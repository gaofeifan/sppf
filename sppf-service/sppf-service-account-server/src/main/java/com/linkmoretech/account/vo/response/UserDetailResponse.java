package com.linkmoretech.account.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alec
 * Description: 编辑用户详情
 * @date: 18:16 2019-06-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponse {

    private Long id;

    private String mobile;

    private List<Long> roles;

}
