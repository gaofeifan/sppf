package com.linkmoretech.user.repository;

import com.linkmoretech.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 用户数据操作层
 * @Author: alec
 * @Description:
 * @date: 下午3:20 2019/4/10
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String>, JpaSpecificationExecutor {

    /**
     * 根据用户手机号查询用户信息
     * */
    UserInfo findUserInfoByUserMobile(String userMobile);

    /**
     * 更新用户状态
     * */
    @Query(value = "update t_user_info set user_status=?2 where id=?1", nativeQuery = true)
    @Modifying
    Integer updateState(String id, Integer status);

    /**
     * 批量更新用户类型
     * @param userIds 用户ID集合
     * @param type 类型
     * @return 返回更新数
     * */
    @Query(value = "update t_user_info set user_type=?2 where id in ?1", nativeQuery = true)
    @Modifying
    Integer updateUserType(List<String> userIds, Integer type);
}
