package com.linkmoretech.parking.repository;

import com.linkmoretech.parking.entity.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 车场地理坐标
 * @Author: alec
 * Description: 车场坐标数据操作层
 * @date: 18:05 2019-05-07
 */
public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {

    /**
     * 根据车场ID查询对应的坐标数据
     * @param carParkId 车场ID
     * @return 坐标集合ß
     * */
    List<Coordinate> getAllByCarParkId(Long carParkId);

    /**
     * 根据车场ID删除已添加地图坐标数据
     * @param carParkId 车场ID
     * */
    void deleteAllByCarParkId(Long carParkId);
}
