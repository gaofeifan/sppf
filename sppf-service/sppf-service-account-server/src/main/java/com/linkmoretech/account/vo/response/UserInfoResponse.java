package com.linkmoretech.account.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 16:44 2019-06-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private String username;

    private List<String> menu;
}
