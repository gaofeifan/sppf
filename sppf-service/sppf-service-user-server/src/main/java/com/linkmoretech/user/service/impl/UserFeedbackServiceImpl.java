package com.linkmoretech.user.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linkmoretech.user.entity.UserFeedback;
import com.linkmoretech.user.repository.UserFeedbackRepository;
import com.linkmoretech.user.service.UserFeedbackService;
import lombok.extern.slf4j.Slf4j;

/**
 * 个人版版本管理
 * @author jhb
 * @Date 2019年6月27日 下午1:53:12
 * @Version 1.0
 */
@Service
@Slf4j
public class UserFeedbackServiceImpl implements UserFeedbackService {

    @Autowired
    UserFeedbackRepository userFeedbackRepository;

	@Override
	public void save(String content, Long userId) {
		UserFeedback feed = new UserFeedback();
		feed.setContent(content);
		feed.setUserId(userId);
		feed.setCreateTime(new Date());
		feed.setUpdateTime(new Date());
		log.info("content = {}",content);
		userFeedbackRepository.save(feed);
	}
}
