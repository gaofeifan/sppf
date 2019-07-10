package com.linkmoretech.versatile.service.impl;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.entity.TimingSchedule;
import com.linkmoretech.versatile.repository.TimingScheduleRepository;
import com.linkmoretech.versatile.service.TimingScheduleService;
import com.linkmoretech.versatile.time.DefaultSchedulingConfigurer;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务service接口
 * @author jhb
 * @Date 2019年7月6日 下午3:54:07
 * @Version 1.0
 */
@Service
@Slf4j
public class TimingScheduleServiceImpl implements TimingScheduleService {
	
	@Autowired
	TimingScheduleRepository timingScheduleRepository;
	
	@Autowired
	private DefaultSchedulingConfigurer defaultSchedulingConfigurer;
	
	@Override
	public List<TimingSchedule> list(int status) {
		List<TimingSchedule> list = this.timingScheduleRepository.getAllByStatus(status);
		log.info("schedule list = {}",JSON.toJSON(list));
		return list;
	}

	@Override
	public void save(TimingSchedule timingSchedule) {
		timingScheduleRepository.save(timingSchedule);
		//	新增定时任务 为启动状态时添加定时任务
		if(timingSchedule.getStatus() == 1){
			defaultSchedulingConfigurer.addTriggerTask(timingSchedule);
		}
	}

	@Override
	public void update(TimingSchedule timingSchedule) {
		this.timingScheduleRepository.saveAndFlush(timingSchedule);
		//	修改定时任务  为启动状态时重置任务   为关闭状态时 取消之前的任务
		if(timingSchedule.getStatus() == 1){
			defaultSchedulingConfigurer.resetTriggerTask(timingSchedule);
		}else{
			defaultSchedulingConfigurer.cancelTriggerTask(timingSchedule.getId()+"");
		}
	}

	@Override
	public void deleteById(List<Long> ids) {
		for(Long id : ids) {
			this.timingScheduleRepository.deleteById(id);
			//	删除时 取消任务
			defaultSchedulingConfigurer.cancelTriggerTask(id.toString());
		}
	}

	@Override
	public PageDataResponse<TimingSchedule> searchPage(@Valid PageSearchRequest pageSearchRequest) {
		Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest.getPageSize());
        Page<TimingSchedule> timingSchedulePage = timingScheduleRepository.findAll(pageable);
        PageDataResponse<TimingSchedule> pageDataResponse = new PageDataResponse<>();
        pageDataResponse.setTotal(timingSchedulePage.getTotalElements());
        List<TimingSchedule> userAppVersionList = timingSchedulePage.getContent();
        pageDataResponse.setData(userAppVersionList);
        return pageDataResponse;
	}
    
}
