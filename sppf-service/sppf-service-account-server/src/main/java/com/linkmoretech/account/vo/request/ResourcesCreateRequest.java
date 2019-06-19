package com.linkmoretech.account.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 16:18 2019-05-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourcesCreateRequest {

    private String clientId;

    private Long parentId;

    private Integer resourceType;

    private String resourceName;

    private String resourceCode;

    private String routerName;

    private Integer resourceSort;
}
