package com.linkmoretech.versatile.time;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import com.linkmoretech.versatile.entity.TimingSchedule;
import com.linkmoretech.versatile.service.TimingScheduleService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StartupRunner implements CommandLineRunner,ApplicationContextAware {
	@Autowired
	private DefaultSchedulingConfigurer defaultSchedulingConfigurer;
	@Autowired
	private TimingScheduleService timingScheduleService;
	@Override
	public void run(String... args) throws Exception {
		log.info("start timing thread ..........");
		new Thread() {
			public void run() {
				try {
					// 等待任务调度初始化完成
					while (!defaultSchedulingConfigurer.inited()) {
						Thread.sleep(100);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				List<TimingSchedule> list = timingScheduleService.list(1);
				defaultSchedulingConfigurer.addTriggerTaskALL(list);
				log.info("end timing thread ..........");
			}
		}.start();

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		DefaultSchedulingConfigurer.context = applicationContext;
	}

}
