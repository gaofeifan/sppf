package com.linkmoretech.account.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description: 资源详情
 * @date: 13:40 2019-06-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceResponse {

    private Long id;

    private String resourceName;

    private String routerName;

    private Integer resourceSort;
}
