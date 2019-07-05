package com.linkmoretech.versatile.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.entity.UserAppVersion;
import com.linkmoretech.versatile.entity.UserVersion;
import com.linkmoretech.versatile.repository.UserAppVersionRepository;
import com.linkmoretech.versatile.repository.UserVersionRepository;
import com.linkmoretech.versatile.service.UserAppVersionService;
import com.linkmoretech.versatile.vo.request.UserAppVersionRequest;
import com.linkmoretech.versatile.vo.response.UserAppVersionPageResponse;
import com.linkmoretech.versatile.vo.response.UserVersionRequest;

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
	public void report(UserVersionRequest uvr, Long userId) {
		UserVersion version = null;
		boolean falg = false;
		version = this.userVersionRepository.findByUserIdAndSystem(userId, 1);
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

	@Override
	public void saveApp(UserAppVersionRequest versionRequest) {
		UserAppVersion userAppVersion = new UserAppVersion();
		BeanUtils.copyProperties(versionRequest, userAppVersion);
		userAppVersion.setCreateTime(new Date());
		userAppVersionRepository.save(userAppVersion);
	}

	@Override
	public void updateApp(UserAppVersionRequest versionRequest) {
		UserAppVersion userAppVersion = new UserAppVersion();
		BeanUtils.copyProperties(versionRequest, userAppVersion);
		userAppVersion.setUpdateTime(new Date());
		userAppVersionRepository.saveAndFlush(userAppVersion);
	}

	@Override
	public void deleteAppById(List<Long> ids) {
		userAppVersionRepository.deleteUserAppVersionByIdIn(ids);
	}

	@Override
	public PageDataResponse<UserAppVersionPageResponse> searchPage(PageSearchRequest pageSearchRequest) {
        Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest.getPageSize());
        Page<UserAppVersion> userAppVersionPage = userAppVersionRepository.findAll(pageable);
        PageDataResponse<UserAppVersionPageResponse> pageDataResponse = new PageDataResponse<>();
        pageDataResponse.setTotal(userAppVersionPage.getTotalElements());
        List<UserAppVersion> userAppVersionList = userAppVersionPage.getContent();
        List<UserAppVersionPageResponse> userAppVersionPageResponses = userAppVersionList.stream().map(userAppVersion -> {
        	UserAppVersionPageResponse userAppVersionPageResponse = new UserAppVersionPageResponse();
            BeanUtils.copyProperties(userAppVersion, userAppVersionPageResponse);
            return userAppVersionPageResponse;
        }).collect(Collectors.toList());
        pageDataResponse.setData(userAppVersionPageResponses);
        return pageDataResponse;
    }
    
}
