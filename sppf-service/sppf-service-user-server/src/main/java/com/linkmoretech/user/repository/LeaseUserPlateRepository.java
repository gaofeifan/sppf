package com.linkmoretech.user.repository;

import com.linkmoretech.user.entity.LeaseUserPlate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 16:22 2019-05-22
 */
public interface LeaseUserPlateRepository extends JpaRepository<LeaseUserPlate, Long> {

    /**
     * 根据长租序列号删除车牌信息
     * @param leaseCodes 长租授权ID
     * */
    void deleteAllByLeaseCodeIn(List<String> leaseCodes);
}
