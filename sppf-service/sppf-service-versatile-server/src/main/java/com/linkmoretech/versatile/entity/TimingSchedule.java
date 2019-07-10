package com.linkmoretech.versatile.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.linkmoretech.versatile.time.util.Cron;
import com.linkmoretech.versatile.time.util.MethodName;
import com.linkmoretech.versatile.time.util.Path;
import com.linkmoretech.versatile.time.util.TaskId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定时任务
 * @author jhb
 * @Date 2019年7月5日 下午5:40:38
 * @Version 1.0
 */
@Entity
@Table(name = "v_timing_schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimingSchedule {
	
	@Id
	@TaskId
	private Integer id;
	
	private String name;
	
	@Path
	private String path;
	
	@MethodName
	private String methodName;
	
	@Cron
	private String cron;
	
	private String description;
	
	private Integer status;

	
}
