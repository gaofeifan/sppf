package com.linkmoretech.versatile.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.linkmoretech.versatile.entity.UserAppVersion;
/**
 * 用户版本
 * @author jhb
 * @Date 2019年6月27日 上午10:53:47
 * @Version 1.0
 */
public interface UserAppVersionRepository extends JpaRepository<UserAppVersion, Long> {

    @Query(value =" select * from v_user_app_version where type = ?1 and status= 1 and scope != 2 order by code desc", nativeQuery = true)
	List<UserAppVersion> findByTypeAndStatus(Integer source);
    
	void deleteUserAppVersionByIdIn(List<Long> ids);

}
