package com.linkmoretech.versatile.service;

import java.util.List;
import javax.validation.Valid;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.entity.TimingSchedule;

/**
 * 定时任务service
 * @author jhb
 * @Date 2019年7月5日 下午6:01:32
 * @Version 1.0
 */
public interface TimingScheduleService {

    /**
     * 定时任务list
     * @param status
     * @return
     */
	List<TimingSchedule> list(int status);

	void save(TimingSchedule timingSchedule);

	void update(TimingSchedule timingSchedule);

	void deleteById(List<Long> ids);

	PageDataResponse<TimingSchedule> searchPage(@Valid PageSearchRequest searchRequest);
}
