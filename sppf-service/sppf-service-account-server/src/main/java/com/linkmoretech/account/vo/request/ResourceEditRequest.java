package com.linkmoretech.account.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 13:43 2019-06-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceEditRequest {

    private Long id;

    private String resourceName;

    private String routerName;

    private Integer resourceSort;
}
