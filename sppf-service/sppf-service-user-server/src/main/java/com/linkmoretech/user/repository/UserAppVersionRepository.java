package com.linkmoretech.user.repository;

import com.linkmoretech.user.entity.UserAppVersion;
import com.linkmoretech.user.entity.UserVersion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 * 用户版本
 * @author jhb
 * @Date 2019年6月27日 上午10:53:47
 * @Version 1.0
 */
public interface UserAppVersionRepository extends JpaRepository<UserVersion, Long> {

    @Query(value =" select * from t_user_app_version where type = ?1 and status= 1 and scope != 2 order by code desc", nativeQuery = true)
	List<UserAppVersion> findByTypeAndStatus(Integer source);

}
