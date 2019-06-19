package com.linkmoretech.account.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description: 权限资源列表响应
 * @date: 16:30 2019-05-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourcesListResponse {

    private Long id;

    private Long parentId;

    private String clientId;

    private Integer resourceType;

    private String resourceName;

    private String routerName;

    private Integer resourceStatus;

    private Integer resourceSort;

}
