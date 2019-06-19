package com.linkmoretech.parking.repository;

import com.linkmoretech.parking.entity.LicensePlate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author: alec
 * @Description: 固定车位绑定车牌号数据操作层
 * @date: 12:01 PM 2019/4/29
 */
public interface LicensePlateRepository extends JpaRepository<LicensePlate, Long> {

    /**
     * 根据id集合查询
     * @param placeIds 车位ID list
     * @return 车牌信息列表
     * */
    List<LicensePlate> getAllByPlaceIdIn(List<Long> placeIds);

    /**
     * 根据车场ID 和 车位ID 删除对应长租车牌号
     * @param parkId 车场ID
     * @param placeId 车位ID
     * */
    void deleteAllByParkIdAndPlaceId(Long parkId, Long placeId);

    /**
     * 删除对应车牌号
     * @param leaseCode 长租授权码
     * @param leaseCode 车牌
     * */
    void deleteAllByLicensePlateNoInAndLeaseCode(Set<String> licensePlateNo, String leaseCode);

    /**
     * 更新车牌号对应车位的状态
     * @param parkId 车位列表
     * @param placeId 车位
     * @param status 状态
     * */
    @Query(value = "update p_license_plate set plate_status=?3 " +
            " where park_id = ?1 and  place_id =?2", nativeQuery = true)
    @Modifying
    void updateLicensePlateStatus(Long parkId, Long placeId, Integer status);

    /**
     * 根据车牌号查询长租车位信息
     * @param licensePlateNo 车牌号
     * @param leaseCode 车区授权码
     * @return 车牌信息集合
     * */
    List<LicensePlate> getAllByLicensePlateNoInAndLeaseCode(List<String> licensePlateNo, String leaseCode);

    /**
     * 根据车位号查询长租车位信息
     * @param placeNo 车牌号
     * @param leaseCode 车区授权码
     * @return 车牌信息集合
     * */
    List<LicensePlate> getAllByPlaceNoAndLeaseCode(String placeNo, String leaseCode);

    /**
     * 根据车位号查询长租车位信息
     * @param leaseCode 车区授权码
     * @return 车牌信息集合
     * */
    List<LicensePlate> getAllByLeaseCode(String leaseCode);
}
