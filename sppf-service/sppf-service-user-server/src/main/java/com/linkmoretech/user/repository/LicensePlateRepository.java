package com.linkmoretech.user.repository;

import com.linkmoretech.user.entity.LicensePlate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author: alec
 * @Description:
 * @date: 下午8:32 2019/4/10
 */
public interface LicensePlateRepository extends JpaRepository<LicensePlate, Long> , JpaSpecificationExecutor {

    /**
     * 根据车牌号查询车牌信息
     * @param plateNo 车牌号
     * @return 车牌信息
     * */
    LicensePlate findByPlateNo(String plateNo);

    /**
     * 根据用户id查询该用户的车牌列表
     * @param userIds 用户集合
     * @return 车牌集合
     * */
    List<LicensePlate> findAllByUserIdIn(List<Long> userIds);

    /**
     * 根据用户ID查询车牌号
     * @param userId 用户ID
     * @return 返回车牌号列表
     * */
    List<LicensePlate> findAllByUserId(Long userId);

    /**
     * 根据车牌号集合查询车牌所属用户
     * @param plateNoList 车牌号集合
     * @return 车牌实体集合
     * */
    List<LicensePlate> findAllByPlateNoIn(List<String> plateNoList);
    
    /**
     * 根据车牌号和用户id查询车牌
     * @param plateNo
     * @param userId
     * @return
     */
	List<LicensePlate> findByPlateNoAndUserId(String plateNo, String userId);

}
