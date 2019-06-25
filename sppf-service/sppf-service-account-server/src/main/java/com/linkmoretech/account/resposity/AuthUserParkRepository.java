package com.linkmoretech.account.resposity;

import com.linkmoretech.account.entity.AuthUserPark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 16:34 2019-06-24
 */
public interface AuthUserParkRepository extends JpaRepository<AuthUserPark, Long> {

    /**
     * 根据用户可车场ID 查询权限
     * @param userId 用户ID
     * @param parkIdIn 车场ID
     * @return 车场权限数据
     * */
    List<AuthUserPark> getAllByUserIdAndParkIdIn(Long userId, List<Long> parkIdIn);

    /**
     *
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 车场权限数据
     * */
    List<AuthUserPark> getAllByUserId(Long userId);


    /**
     * 根据用户ID 和当前车场ID 查询其 车位操作权限
     *
     * @param userId 用户ID
     *
     * @param parkId 车场ID
     *
     * @return 车场权限
     * */
    AuthUserPark getByUserIdAndParkId(Long userId, Long parkId);

}
