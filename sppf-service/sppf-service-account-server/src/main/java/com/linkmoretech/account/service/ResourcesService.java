package com.linkmoretech.account.service;

import com.linkmoretech.account.vo.request.ResourceEditRequest;
import com.linkmoretech.account.vo.request.SearchRequest;
import com.linkmoretech.account.vo.request.ResourcesCreateRequest;
import com.linkmoretech.account.vo.response.ResourceResponse;
import com.linkmoretech.account.vo.response.ResourcesAllResponse;
import com.linkmoretech.account.vo.response.ResourcesListResponse;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 16:16 2019-05-29
 */
public interface ResourcesService {


    /**
     * 添加资源
     * @param resourcesCreateRequest 添加资源参数
     * @throws CommonException 自定义验证异常
     * */
    void createResource(ResourcesCreateRequest resourcesCreateRequest) throws CommonException;

    /**
     * 分页查询资源数据
     * @param pageSearchRequest 查询条件
     * @return 封装数据
     * */
    PageDataResponse<ResourcesListResponse> searchList(PageSearchRequest<SearchRequest> pageSearchRequest);

    /**
     * 删除资源
     * @param id 资源ID
     * @throws CommonException 自定义异常
     * */
    void remove(Long id) throws CommonException;

    /**
     * 更新资源上下线
     * @param id 资源ID
     * @param status 状态
     * @throws CommonException 自定义异常
     * */
    void updateStatus(Long id, Integer status) throws CommonException;


    /**
     * 获取所有资源数据
     * */
    List<ResourcesAllResponse> getAllResources(String clientId);


    ResourceResponse resourceInfo(Long id) throws CommonException;


    void  update(ResourceEditRequest resourceEditRequest) throws CommonException;
}
