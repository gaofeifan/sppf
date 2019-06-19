package com.linkmoretech.account.service;

import com.linkmoretech.account.vo.request.RolesAddResourceRequest;
import com.linkmoretech.account.vo.request.RolesCreateRequest;
import com.linkmoretech.account.vo.request.RolesEditRequest;
import com.linkmoretech.account.vo.response.RolesListResponse;
import com.linkmoretech.common.exception.CommonException;

import java.util.List;
import java.util.Map;

/**
 * @Author: alec
 * Description: 角色服务逻辑
 * @date: 14:39 2019-05-30
 */
public interface RolesService {

    /**
     * 创建角色
     * @param rolesCreateRequest 角色创建参数
     * @return rolesId 返回创建角色ID
     * @throws CommonException 自定义验证异常
     * */
    Long create(RolesCreateRequest rolesCreateRequest) throws CommonException;

    /**
     * 角色列表
     * @param clientId 客户端ID
     * @return 角色列表
     * */
    List<RolesListResponse> list(String clientId);

    /**
     * 根据角色ID查询管理资源ID
     * @param rolesId 角色ID
     * @return 资源ID集合
     * */
    List<Long> getResourcesIdByRolesId(Long rolesId);

    /**
     * 角色授权
     * @param rolesAddResourceRequest 授权参数
     * */
    void addResourcesForRoles(RolesAddResourceRequest rolesAddResourceRequest) throws CommonException;


    /**
     * 按客户端分组查询角色
     * @return 返回分组后对角色
     * */
    Map<String, List<RolesListResponse>> getRolesGroup();

    /**
     * 删除角色
     * @param rolesId 角色ID
     * */
    void remove(Long rolesId) throws CommonException;

    /**
     * 编辑角色
     * @param rolesEditRequest 角色信息
     * */
    Long update(RolesEditRequest rolesEditRequest) throws CommonException ;
}
