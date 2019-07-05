package com.linkmoretech.versatile.repository;

import com.linkmoretech.versatile.entity.StaffAppVersion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: jiaohanbin
 * @Description:
 * @date: 8:32 PM 2019/4/29
 */
public interface StaffAppVersionRepository extends JpaRepository<StaffAppVersion, Long> {
    
	@Query(value =" select * from v_staff_app_version where type = ?1 and status= 1 order by code desc limit 0,1", nativeQuery = true)
    List<StaffAppVersion> findByTypeAndStatus(int appType);
    
}
