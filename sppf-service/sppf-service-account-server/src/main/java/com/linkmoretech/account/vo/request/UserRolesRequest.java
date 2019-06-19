package com.linkmoretech.account.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alec
 * Description: 用户角色参数
 * @date: 14:47 2019-05-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesRequest {

    /**
     * 对应客户端值
     * */
    private String clientId;

    /**
     * 对应角色
     * */
    private List<Long> rolesId;
}
