package com.linkmoretech.account.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 14:41 2019-05-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesListResponse {

    private Long id;

    private String rolesName;

    private String clientId;

    private String content;
}
