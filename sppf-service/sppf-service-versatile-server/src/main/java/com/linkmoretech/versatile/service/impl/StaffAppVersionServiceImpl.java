package com.linkmoretech.versatile.service.impl;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.entity.StaffAppVersion;
import com.linkmoretech.versatile.repository.StaffAppVersionRepository;
import com.linkmoretech.versatile.service.StaffAppVersionService;
import com.linkmoretech.versatile.vo.request.StaffAppVersionCreateRequest;
import com.linkmoretech.versatile.vo.request.StaffAppVersionEditRequest;
import com.linkmoretech.versatile.vo.response.StaffAppVersionPageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jhb
 * @Description:
 * @date: 8:36 PM 2019/4/29
 */
@Service
@Slf4j
public class StaffAppVersionServiceImpl implements StaffAppVersionService {

    @Autowired
    StaffAppVersionRepository staffAppVersionRepository;

    @Override
    public void create(StaffAppVersionCreateRequest staffAppVersionCreateRequest) throws CommonException {
        StaffAppVersion staffAppVersion = new StaffAppVersion();
        BeanUtils.copyProperties(staffAppVersionCreateRequest, staffAppVersion);
        staffAppVersion.setCreateTime(new Date());
        staffAppVersion.setUpdateTime(new Date());
        staffAppVersionRepository.save(staffAppVersion);
    }

    @Override
    public void edit(StaffAppVersionEditRequest staffAppVersionEditRequest) throws CommonException {
        StaffAppVersion staffAppVersion = new StaffAppVersion();
        BeanUtils.copyProperties(staffAppVersionEditRequest, staffAppVersion);
        staffAppVersion.setCreateTime(new Date());
        staffAppVersion.setUpdateTime(new Date());
        staffAppVersionRepository.saveAndFlush(staffAppVersion);
    }

    @Override
    public void delete(Long id) {
        staffAppVersionRepository.deleteById(id);
    }

    @Override
    public PageDataResponse<StaffAppVersionPageResponse> searchPage(PageSearchRequest pageSearchRequest) {
        Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest.getPageSize());
        Page<StaffAppVersion> staffAppVersionPage = staffAppVersionRepository.findAll(pageable);
        PageDataResponse<StaffAppVersionPageResponse> pageDataResponse = new PageDataResponse<>();
        pageDataResponse.setTotal(staffAppVersionPage.getTotalElements());
        List<StaffAppVersion> staffAppVersionList = staffAppVersionPage.getContent();
        List<StaffAppVersionPageResponse> staffAppVersionPageResponses = staffAppVersionList.stream().map(staffAppVersion -> {
            StaffAppVersionPageResponse staffAppVersionPageResponse = new StaffAppVersionPageResponse();
            BeanUtils.copyProperties(staffAppVersion, staffAppVersionPageResponse);
            return staffAppVersionPageResponse;
        }).collect(Collectors.toList());
        pageDataResponse.setData(staffAppVersionPageResponses);
        return pageDataResponse;
    }
}
