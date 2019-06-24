package com.linkmoretech.parking.repository;

import com.linkmoretech.parking.entity.LeasePlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: alec
 * Description: 长租车位数据操作层
 * @date: 18:04 2019-05-08
 */
public interface LeasePlaceRepository extends JpaRepository<LeasePlace, Long> {

    /**
     * 根据授权码查询长租授权信息
     * @param leaseCode 授权码
     * @return 长租信息
     * */
    LeasePlace getFirstByLeaseCode(String leaseCode);
}
