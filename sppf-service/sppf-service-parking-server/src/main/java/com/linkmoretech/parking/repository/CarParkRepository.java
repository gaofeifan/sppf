package com.linkmoretech.parking.repository;

import com.linkmoretech.parking.entity.CarPark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: alec
 * @Description: 车场数据操作层
 * @date: 11:35 AM 2019/4/29
 */
public interface CarParkRepository extends JpaRepository<CarPark, Long> {

    /**
     * 根据城市编码查询车场
     * @param cityCode 城市编码
     * @return 车场列表
     * */
    List<CarPark> findAllByCityCode(String cityCode);
}
