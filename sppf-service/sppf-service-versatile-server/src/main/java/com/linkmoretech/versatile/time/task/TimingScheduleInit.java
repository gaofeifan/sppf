package com.linkmoretech.versatile.time.task;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.linkmoretech.versatile.entity.TimingSchedule;
import com.linkmoretech.versatile.service.TimingScheduleService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TimingScheduleInit {

	@Autowired
	private TimingScheduleService timing;

	public void run() {
		log.info(".................timing schedule init...............");
		List<TimingSchedule> list = timing.list(1);
		log.info(".................timing schedule init...............",JSON.toJSON(list));
	}

}
