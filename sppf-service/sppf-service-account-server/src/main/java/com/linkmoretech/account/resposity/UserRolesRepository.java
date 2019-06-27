package com.linkmoretech.account.resposity;

import com.linkmoretech.account.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 14:41 2019-05-31
 */
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {

    /**
     * 根据用户ID 查询 对应角色
     * @param userIdList 用户ID
     * @return 关联数据集合
     * */

    List<UserRoles> getAllByUserIdIn(List<Long> userIdList);

    /**
     * 根据用户ID 查询 对应角色
     * @param userId 用户ID
     * @return 关联数据集合
     * */
    List<UserRoles> getAllByUserId(Long userId);


    /**
     * 根据用户ID删除角色
     * @param userId 用户ID
     * */
    void deleteAllByUserId(Long userId);
}
