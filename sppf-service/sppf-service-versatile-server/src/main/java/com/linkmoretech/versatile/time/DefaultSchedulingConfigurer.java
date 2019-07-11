package com.linkmoretech.versatile.time;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.linkmoretech.common.config.RedisService;
import com.linkmoretech.versatile.entity.TimingSchedule;
import com.linkmoretech.versatile.time.util.Cron;
import com.linkmoretech.versatile.time.util.MethodName;
import com.linkmoretech.versatile.time.util.Path;
import com.linkmoretech.versatile.time.util.TaskId;
import lombok.extern.slf4j.Slf4j;

/**
 * 调度中心配置
 * @author jhb
 * @Date 2019年7月6日 下午2:59:23
 * @Version 1.0
 */
@EnableScheduling
@Component
@Slf4j
public class DefaultSchedulingConfigurer implements SchedulingConfigurer {
	//	用于查询任务
	private final String FIELD_SCHEDULED_FUTURES = "scheduledTasks";
	//	任务id前缀
	private final String TASK = "TASK";
	
	private ScheduledTaskRegistrar taskRegistrar;
	//	上下文
	public static ApplicationContext context;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Resource
	private RedisService redisService;
	
	private static final String LOCK = "task-job-lock";
	
	private static final String KEY = "tasklock";
	
	private Set<ScheduledFuture<?>> scheduledFutures = null;
	// 索引map 用于更新，取消数据时查询对应的任务
	private Map<String, ScheduledFuture<?>> taskFutures = new ConcurrentHashMap<String, ScheduledFuture<?>>();

	@Override
	public void configureTasks(ScheduledTaskRegistrar tr) {
		taskRegistrar = tr;
	}

	@SuppressWarnings("unchecked")
	private Set<ScheduledFuture<?>> getScheduledFutures() {
		if (scheduledFutures == null) {
			try {
				scheduledFutures = (Set<ScheduledFuture<?>>) BeanUtils.getProperty(taskRegistrar,
						FIELD_SCHEDULED_FUTURES);
			} catch (NoSuchFieldException e) {
				throw new SchedulingException("not found scheduledFutures field.");
			}
		}
		return scheduledFutures;
	}

	
	/**
	 * 添加调度任务（请使用addTriggerTask(Object obj)）
	 * @param taskId
	 * @param ts
	 */
	@Deprecated
	public void addTriggerTask(String taskId, TimingSchedule ts) {
		//	判断任务是否已经存在  
		if (taskFutures.containsKey(taskId)) {
			throw new SchedulingException("the taskId[" + taskId + "] was added.");
		}
		TriggerTask triggerTask = getTriggerTask(ts);
		TaskScheduler scheduler = taskRegistrar.getScheduler();
		ScheduledFuture<?> future = scheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
		getScheduledFutures().add(future);
		taskFutures.put(taskId, future);
	}

	/**
	 * 将所有的调度任务启动
	 * @param list
	 */
	public void  addTriggerTaskALL(List<?> list) {
		log.info("添加所有的调度任务");
		for (Object obj : list) {
			log.info("执行调度任务【" + obj.toString() + "】");
			addTriggerTask(obj);
			log.info("调度任务执行完成【" + obj.toString() + "】");
		}
	}
	
	/**
	 * 添加调度任务
	 * @param obj
	 */
	public void addTriggerTask(Object obj) {
			//	判断任务是否已经存在  
			Object taskId = BeanUtils.getAnnottionFieldValue(obj, TaskId.class);
			if(taskId != null){
				String taskNumber = TASK + taskId.toString();
				if (taskFutures.containsKey(taskId.toString())) {
					throw new SchedulingException("the taskId[" + taskNumber + "] was added.");
				}
				log.info("taskFutures = {} taskNumber = {}",JSON.toJSON(taskFutures), taskNumber);
				//	解析对象获取任务
				TriggerTask triggerTask = getTriggerTask(obj);
				TaskScheduler scheduler = taskRegistrar.getScheduler();
				ScheduledFuture<?> future = scheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
				getScheduledFutures().add(future);
				taskFutures.put(taskNumber, future);
			}else{
				log.error("id不存在【"+obj.toString()+"】");
			}
	}

	/**
	 * 通过调度任务获取TriggerTask对象
	 * @param obj
	 * @return
	 */
	private TriggerTask getTriggerTask(Object obj) {
		return new TriggerTask(new Runnable() {
			@Override
			public void run() {
				log.info("开始启动线程............");
				// 根据类名获取spring容器中的对象
				if (context == null) {
					throw new RuntimeException("没有获取到容器对象");
				}
				try {
					Object path = BeanUtils.getAnnottionFieldValue(obj, Path.class);
					if(path != null ){
						//	通过类名找到对应的class
						Class<?> name = Class.forName(path.toString());
						Object bean = context.getBean(name);
						// 对象为空时的处理
						if (bean == null) {
							log.error("类名没有找到 数据【" + path.toString() + "】");
						}
						// 通过反射调用调度方法
						Object object = BeanUtils.getAnnottionFieldValue(obj, MethodName.class);
						if(object != null){
							Method method = bean.getClass().getMethod(object.toString());
							log.info(">>>>>>>>>>>>>>>>>>start schedule>>>>>>>>>>>>>>>>>");
							
							Object taskId = BeanUtils.getAnnottionFieldValue(obj, TaskId.class);
							String taskNumber = "TASK";
							if(taskId != null){
								taskNumber = TASK + taskId.toString();
							}
							boolean lock = false;
					    	try{
					    		// 获取锁
					        	lock = stringRedisTemplate.opsForValue().setIfAbsent(taskNumber, LOCK);
					        	log.info("是否获取到锁:{}",lock);
					        	if(lock){
					        		// 如果在执行任务的过程中，程序突然挂了，为了避免程序因为中断而造成一直加锁的情况产生，20分钟后，key值失效，自动释放锁，
					        		stringRedisTemplate.expire(taskNumber, 10, TimeUnit.MINUTES);
					        		log.info("..............获取权限，开始具体执行任务...............");
					        		
					        		//执行具体任务
					        		method.invoke(bean);
					        	}else{
					        		log.info("没有获取到锁，不执行任务!");
					        		return;
					        	}
					    	}finally{// 无论如何，最终都要释放锁
					    		if(lock){// 如果获取了锁，则释放锁
					    			stringRedisTemplate.delete(taskNumber);
					    			log.info("任务结束，释放锁!");
					    		}else{
					    			log.info("没有获取到锁，无需释放锁!");
					    		}
					    	}
							
							log.info(">>>>>>>>>>>>>>>>>>end schedule>>>>>>>>>>>>>>>>>");
						}else{
							log.error("methodName 为空【"+obj.toString()+"】【"+object+"】");
						}
					}else{
						log.error("path 为空【"+obj.toString()+"】【"+path+"】");
					}
					
				} catch (IllegalArgumentException |   BeansException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}, triggerContext -> {
			Object object = BeanUtils.getAnnottionFieldValue(obj, Cron.class);
			if(object != null){
				log.info("开始解析cron【" + object.toString() + "】");
				// 获取定时任务时间的表达式
				String cron = object.toString();
				// 对cron表达式进行校验
				return new CronTrigger(cron).nextExecutionTime(triggerContext);
			}{
				log.error("cron 为空【"+obj.toString()+"】");
				return null;
			}
		});
	}

	/**
	 * 取消任务
	 * @param taskId
	 */
	public void cancelTriggerTask(String taskId) {
		taskId = TASK + taskId;
		ScheduledFuture<?> future = taskFutures.get(taskId);
		if (future != null) {
			future.cancel(true);
		}
		taskFutures.remove(taskId);
		getScheduledFutures().remove(future);
	}

	/**
	 * 重置任务
	 * @param obj
	 */
	public void resetTriggerTask(Object obj) {
		Object object = BeanUtils.getAnnottionFieldValue(obj, TaskId.class);
		if(object != null){
			cancelTriggerTask(object.toString());
			addTriggerTask(obj);
		}
	}

	/**
	 * 任务编号
	 * @return
	 */
	public Set<String> taskIds() {
		return taskFutures.keySet();
	}

	/**
	 * 是否已存在任务
	 * @param taskId
	 * @return
	 */
	public boolean hasTask(String taskId) {
		return this.taskFutures.containsKey(taskId);
	}

	/**
	 * 任务调度是否已经初始化完成
	 * @return
	 */
	public boolean inited() {
		return this.taskRegistrar != null && this.taskRegistrar.getScheduler() != null;
	}

	/**
	 * 通过调度任务获取TriggerTask对象（请使用getTriggerTask(Object obj)）
	 * @param ts
	 * @return
	 */
	@Deprecated
	private TriggerTask getTriggerTask(TimingSchedule ts){
		return new TriggerTask(new Runnable() {
			@Override
			public void run() {
				log.info("启动线程");
				// 根据类名获取spring容器中的对象
				if (context == null) {
					throw new RuntimeException("没有获取到容器对象");
				}
				try {
					//	通过类名找到对应的class
					Class<?> name = Class.forName(ts.getPath());
					Object bean = context.getBean(name);
					// 对象为空时的处理
					if (bean == null) {
						log.error("类名有误 数据【" + ts.toString() + "】");
					}
					// 通过反射调用调度方法
					Method method = bean.getClass().getMethod(ts.getMethodName());
					method.invoke(bean.getClass().newInstance());
				} catch (IllegalArgumentException | InstantiationException | BeansException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}, triggerContext -> {
			log.info("解析cron【" + ts.getCron() + "】");
			// 获取定时任务时间的表达式
			String cron = ts.getCron();
			// 对cron表达式进行校验
			return new CronTrigger(cron).nextExecutionTime(triggerContext);
		});
	}
}
