package com.linkmoretech.account.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alec
 * Description: 创建用户参数
 * @date: 14:45 2019-05-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    private String userName;

    private String password;

    private String mobile;

    /**
     * 用户操作数据权限，空为所有车场
     * */
    private List<Long> carParkIds;

    private List<UserRolesRequest> roles;

}
