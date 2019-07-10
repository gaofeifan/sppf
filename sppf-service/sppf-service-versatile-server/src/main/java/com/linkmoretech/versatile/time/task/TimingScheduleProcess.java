package com.linkmoretech.versatile.time.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linkmoretech.versatile.service.TimingScheduleService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TimingScheduleProcess {

	@Autowired
	private TimingScheduleService timing;

	public void run() throws InterruptedException {
		log.info(".................timing schedule process...............");
		log.info("hello process");
		log.info(".................timing schedule process...............");
	}

}
