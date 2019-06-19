package com.linkmoretech.account.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description: 创建角色参数封装
 * @date: 14:34 2019-05-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesCreateRequest {

    private String rolesName;

    private String clientId;

    private String content;
}
