package com.linkmoretech.parking.repository;

import com.linkmoretech.parking.entity.CarPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Author: alec
 * @Description: 车位数据操作层
 * @date: 11:38 AM 2019/4/29
 */
public interface CarPlaceRepository extends JpaRepository<CarPlace, Long>, JpaSpecificationExecutor<CarPlace> {
    /**
     * 根据车场 和 id集合查询符合条件的车位列表
     * @param parkId 车场ID
     * @param idList id列表
     * @return 返回符合条件车位list
     * */
    List<CarPlace> getAllByParkIdAndIdIn(Long parkId, List<Long> idList);
    /**
     * 根据车场 和 车位名称集合查询符合条件的车位列表
     * @param parkId 车场ID
     * @param placeNoList 车位编号
     * @return 返回符合条件车位listß
     * */
    List<CarPlace> getAllByParkIdAndPlaceNoIn(Long parkId, List<String> placeNoList);
    /**
     * 根据车场，车位状态，车位类型，上线状态查询结果
     * @param parkId 车场ID
     * @param lineStatus 在线状态
     * @param placeStatus 车位状态
     * @param placeType 车位类型
     * @return 车位列表
     * */
    List<CarPlace> getAllByParkIdAndPlaceTypeAndLineStatusAndPlaceStatus(Long parkId, Integer placeType,
                                                                       Integer lineStatus,Integer placeStatus);
    /**
     * 查询车场指定车位类型的车位数
     * @param parkId 车场ID
     * @param placeType 车位类型
     * @return 返回车位数
     * */
    long countByParkIdAndPlaceType(Long parkId, Integer placeType);

    /**
     * 查询车场车位总数
     * @param parkId 车场ID
     * @return 返回车位数
     * */
    long countByParkId(Long parkId);
    /**
     * 编辑车位的类型
     * @param idList 车位列表
     * @param placeType 状态
     * @param updateTime 更新时间
     * */
    @Query(value = "update p_car_place set place_type=?2, update_time=?3 , update_by=?4" +
            " where id in (?1)", nativeQuery = true)
    @Modifying
    void updatePlaceType(List<Long> idList, Integer placeType, Date updateTime, String updateBy);

    List<CarPlace> getAllByParkIdIn(List<Long> parkIds);

    CarPlace findByPlaceNoAndParkId(String placeNo, Long parkId);

    @Query(value = "update p_car_place set lock_code=?2 where id in (?1)", nativeQuery = true)
    void updateLockCode(Long id, String lockCode);

    CarPlace getOneByLockCodeAndParkId(String sn, Long parkId);

    @Query(value = "SELECT * FROM p_car_place WHERE park_id = ?1 and type = ?3 and if(?2 = null ,1=1, id in(?2)) and if(?4= null,1=1,place_no like ?4)", nativeQuery = true)
    List<CarPlace> findParkIdAndIdInAndTypeAndPlaceNo(Long carParkId, String placeIds, Integer type, String carPlaceName);
}
