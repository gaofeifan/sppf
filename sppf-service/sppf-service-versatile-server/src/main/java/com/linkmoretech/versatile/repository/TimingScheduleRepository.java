package com.linkmoretech.versatile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkmoretech.versatile.entity.TimingSchedule;

/**
 * @Author: jiaohanbin
 * @Description:
 * @date: 8:32 PM 2019/4/29
 */
public interface TimingScheduleRepository extends JpaRepository<TimingSchedule, Long> {
	
	List<TimingSchedule> getAllByStatus(int status);
    
}
