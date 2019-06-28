package com.linkmoretech.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkmoretech.user.entity.UserFeedback;
/**
 * 用户反馈信息
 * @author jhb
 * @Date 2019年6月27日 上午10:53:47
 * @Version 1.0
 */
public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Long> {

}
