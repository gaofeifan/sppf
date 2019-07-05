package com.linkmoretech.versatile.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.entity.UserVersion;
import com.linkmoretech.versatile.repository.UserVersionRepository;
import com.linkmoretech.versatile.service.UserVersionService;
import com.linkmoretech.versatile.vo.response.UserVersionPageResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 个人版版本管理
 * @author jhb
 * @Date 2019年6月27日 下午1:53:12
 * @Version 1.0
 */
@Service
@Slf4j
public class UserVersionServiceImpl implements UserVersionService {

    @Autowired
    UserVersionRepository userVersionRepository;

	@Override
	public PageDataResponse<UserVersionPageResponse> searchPage(PageSearchRequest pageSearchRequest) {
        Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest.getPageSize());
        Page<UserVersion> userVersionPage = userVersionRepository.findAll(pageable);
        PageDataResponse<UserVersionPageResponse> pageDataResponse = new PageDataResponse<>();
        pageDataResponse.setTotal(userVersionPage.getTotalElements());
        List<UserVersion> userVersionList = userVersionPage.getContent();
        List<UserVersionPageResponse> userVersionPageResponses = userVersionList.stream().map(userVersion -> {
        	UserVersionPageResponse userVersionPageResponse = new UserVersionPageResponse();
            BeanUtils.copyProperties(userVersion, userVersionPageResponse);
            return userVersionPageResponse;
        }).collect(Collectors.toList());
        pageDataResponse.setData(userVersionPageResponses);
        return pageDataResponse;
    }
    
}
