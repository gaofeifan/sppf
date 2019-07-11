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

import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.entity.UnusualLog;
import com.linkmoretech.versatile.repository.UnusualLogRepository;
import com.linkmoretech.versatile.service.UnusualLogService;
import com.linkmoretech.versatile.vo.request.UnusualLogRequest;
import com.linkmoretech.versatile.vo.response.UnusualLogPageResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: jhb
 * @Description:
 * @date: 8:36 PM 2019/4/29
 */
@Service
@Slf4j
public class UnusualLogServiceImpl implements UnusualLogService {

    @Autowired
    UnusualLogRepository unusualLogRepository;

    @Override
    public PageDataResponse<UnusualLogPageResponse> searchPage(PageSearchRequest pageSearchRequest) {
        Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest.getPageSize());
        Page<UnusualLog> UnusualLogPage = unusualLogRepository.findAll(pageable);
        PageDataResponse<UnusualLogPageResponse> pageDataResponse = new PageDataResponse<>();
        pageDataResponse.setTotal(UnusualLogPage.getTotalElements());
        List<UnusualLog> unusualLogList = UnusualLogPage.getContent();
        List<UnusualLogPageResponse> unusualLogPageResponses = unusualLogList.stream().map(unusualLog -> {
            UnusualLogPageResponse unusualLogPageResponse = new UnusualLogPageResponse();
            BeanUtils.copyProperties(unusualLog, unusualLogPageResponse);
            return unusualLogPageResponse;
        }).collect(Collectors.toList());
        pageDataResponse.setData(unusualLogPageResponses);
        return pageDataResponse;
    }

	@Override
	public void save(UnusualLogRequest unusualLogRequest) {
		UnusualLog unusualLog = new UnusualLog();
        BeanUtils.copyProperties(unusualLogRequest, unusualLog);
        unusualLog.setCreateTime(new Date());
		this.unusualLogRepository.save(unusualLog);
	}

}
