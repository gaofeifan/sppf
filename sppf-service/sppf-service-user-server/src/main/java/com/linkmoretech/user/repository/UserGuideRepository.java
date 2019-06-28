package com.linkmoretech.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkmoretech.user.entity.UserGuide;
/**
 * 用户版本
 * @author jhb
 * @Date 2019年6月27日 上午10:53:47
 * @Version 1.0
 */
public interface UserGuideRepository extends JpaRepository<UserGuide, Long> {

}
