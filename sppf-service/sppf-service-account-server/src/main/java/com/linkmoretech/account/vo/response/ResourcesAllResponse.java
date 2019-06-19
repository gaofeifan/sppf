package com.linkmoretech.account.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alec
 * Description: 递归显示所有资源数据
 * @date: 11:40 2019-05-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourcesAllResponse {

    private Long id;

    private Long parentId;

    private String resourceName;

    private Integer status;

    private List<ResourcesAllResponse> children;


}
