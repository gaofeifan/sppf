package com.linkmoretech.user.repository;

import com.linkmoretech.user.entity.UserVersion;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * 用户版本
 * @author jhb
 * @Date 2019年6月27日 上午10:53:47
 * @Version 1.0
 */
public interface UserVersionRepository extends JpaRepository<UserVersion, Long> {

	UserVersion findByUserIdAndSystem(String userId, String string);

}
