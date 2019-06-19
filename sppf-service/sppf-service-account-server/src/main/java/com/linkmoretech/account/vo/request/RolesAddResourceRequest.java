package com.linkmoretech.account.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alec
 * Description: 给角色授权
 * @date: 17:24 2019-05-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesAddResourceRequest {

    private Long rolesId;

    private List<Long> resourcesIdList;
}
