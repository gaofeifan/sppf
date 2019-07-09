package com.linkmoretech.parking.repository;

import com.linkmoretech.parking.entity.FloorPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: alec
 * @Description: 车场分布数据操作层
 * @date: 11:55 AM 2019/4/29
 */
public interface FloorPlanRepository extends JpaRepository<FloorPlan, Long> {

    /**
     * 根据车场ID查询对应车位分布数据
     * @param parkId 车场ID
     * @return 返回车场车位分布
     * */
    List<FloorPlan> getAllByParkId(Long parkId);

    /**
     * 根据车场集合查询对应车位分布
     * @param parkIdList 车场ID集合
     * @return 返回车场分布
     * */
    List<FloorPlan> getAllByParkIdIn(List<Long> parkIdList);

    /**
     * 根据车场ID删除对应车位分布
     * @param parkId 车场ID
     * @return 返回已删除的车场分布数据
     * */
    List<FloorPlan> deleteAllByParkId(Long parkId);

}
