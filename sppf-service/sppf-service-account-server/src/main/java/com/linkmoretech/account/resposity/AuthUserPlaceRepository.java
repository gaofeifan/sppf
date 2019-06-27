package com.linkmoretech.account.resposity;

import com.linkmoretech.account.entity.AuthUserPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 17:05 2019-06-24
 */
public interface AuthUserPlaceRepository extends JpaRepository<AuthUserPlace, Long> {


    /**
     * 根据ID查询授权车位信息
     * @param authIds 授权车场ID
     * @return 授权车位信息
     * */
    List<AuthUserPlace> getAllByAuthDataIdIn(List<Long> authIds);


    /**
     * 根据ID查询授权车位信息
     * @param authId 授权车场ID
     * @return 授权车位信息
     * */
    List<AuthUserPlace> getAllByAuthDataId(Long authId);

    /**
     * 删除车场下车位数据权限
     * @param authId 车场授权ID
     * */
    void deleteAllByAuthDataId(Long authId);
}
