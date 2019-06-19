package com.linkmoretech.account.resposity;

import com.linkmoretech.account.entity.RolesResources;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: alec
 * Description:  角色资源对应数据访问层
 * @date: 16:16 2019-05-30
 */
public interface RolesResourcesRepository extends JpaRepository<RolesResources, Long> {


    /**
     *根据角色ID查询角色资源对应数据
     * @param rolesId 角色ID
     * @return 角色资源关联数据
     * */
    List<RolesResources> getAllByRolesId(Long rolesId);


    /**
     *根据角色ID查询角色资源对应数据
     * @param rolesIds 角色ID
     * @return 角色资源关联数据
     * */
    List<RolesResources> getAllByRolesIdIn(List<Long> rolesIds);

    /**
     * 根据角色删除其授权数据
     * @param rolesId 角色ID
     * */
    void deleteAllByRolesId(Long rolesId);
}
