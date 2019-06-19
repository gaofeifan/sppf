package com.linkmoretech.account.resposity;

import com.linkmoretech.account.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 14:33 2019-05-30
 */
public interface RolesRepository extends JpaRepository<Roles, Long> {

    /**
     * 根据客户端ID 和 角色名称查询角色
     * @param clientId 客户端ID
     * @param rolesName 角色名称
     * @return 已存在对角色
     * */
    Roles findByRolesNameAndClientId(String rolesName, String clientId);

    /**
     * 根据客户端ID查询角色
     * @param clientId 客户端ID
     * @return 角色列表
     * */
    List<Roles> getAllByClientId(String clientId);


    /**
     * 根据ID 查询角色列表
     * @param rolesIdList 角色ID集合
     * @return 角色列表
     * */
    List<Roles> getAllByIdIn(List<Long> rolesIdList);
}
