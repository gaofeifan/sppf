package com.linkmoretech.account.resposity;

import com.linkmoretech.account.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 16:14 2019-05-29
 */
public interface ResourcesRepository extends JpaRepository<Resources, Long> {

    /**
     * 根据资源名称返回资源
     * @param resourceName 资源名称
     * @return 返回资源数据
     * */
    Resources findByResourceName(String resourceName);

    /**
     * 批量删除父节点为ID的记录
     * @param resourceId 资源父节点
     * */
    void deleteAllByParentId(Long resourceId);

    /**
     * 更新资源的可用状态
     * @param resourceId 车位列表
     * @param status 状态
     * */
    @Query(value = "update a_resource set resourceStatus=?2 " +
            " where id = ?1 or  parent_id =?1", nativeQuery = true)
    @Modifying
    void updateEnableStatus(Long resourceId, Integer status);

    /**
     * 根据客户端ID查询父节点不为空的所有资源并排序
     * @param clientId 客户端ID
     * @return 资源列表集合
     * */
    List<Resources> getAllByClientId(String clientId);


    /**
     * 根据客户端ID查询父节点不为空的所有资源并排序
     * @param resourcesIds 客户端ID
     * @return 资源列表集合
     * */
    List<Resources> getAllByIdIn(List<Long> resourcesIds);
}
