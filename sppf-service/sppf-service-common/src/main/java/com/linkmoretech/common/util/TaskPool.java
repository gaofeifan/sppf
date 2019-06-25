package com.linkmoretech.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author GFF
 * @Description线程池
 * @Date 2019/6/17
 */
public class TaskPool {

	static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	
	private static class LazyTaskPool {
        final static TaskPool INSTANCE = new TaskPool();
    }
	
	public static final TaskPool getInstance() {
        return LazyTaskPool.INSTANCE;
    }
	
	public void task(Runnable runnable) {
        cachedThreadPool.execute(runnable);
    }
	
}
