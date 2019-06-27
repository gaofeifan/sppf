package com.linkmoretech.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.linkmoretech.user.entity.UserAppVersion;
import com.linkmoretech.user.entity.UserVersion;
import com.linkmoretech.user.repository.UserAppVersionRepository;
import com.linkmoretech.user.repository.UserVersionRepository;
import com.linkmoretech.user.service.UserAppVersionService;
import com.linkmoretech.user.vo.UserVersionRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 个人版版本管理
 * @author jhb
 * @Date 2019年6月27日 下午1:53:12
 * @Version 1.0
 */
@Service
@Slf4j
public class UserAppVersionServiceImpl implements UserAppVersionService {

    @Autowired
    UserAppVersionRepository userAppVersionRepository;

    @Autowired
    UserVersionRepository userVersionRepository;

	@Override
	public UserAppVersion currentAppVersion(Integer source) {
		List<UserAppVersion> userAppVersions = this.userAppVersionRepository.findByTypeAndStatus(source);
		return userAppVersions.get(0);
	}

	@Override
	public void report(UserVersionRequest uvr, String userId) {
		UserVersion version = null;
		boolean falg = false;
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("system", 1);
		version = this.userVersionRepository.findByUserIdAndSystem(userId, "1");
		log.info("report = {}",JSON.toJSON(version));
		if(version != null) {
			falg = true;
		}else {
			version = new UserVersion();
			version.setUserId(userId);
		}
		BeanUtils.copyProperties(uvr, version);
		version.setCommitTime(new Date());
		version.setSystem(1);
		if(falg) {
			this.userVersionRepository.saveAndFlush(version);
			return;
		}
		this.userVersionRepository.save(version);
	}
    
}
