package com.linkmoretech.parking.service.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.linkmoretech.common.config.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.util.SpringUtil;
import com.linkmoretech.parking.entity.ResLockMessage;
import com.linkmoretech.parking.service.LockService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: GFF
 * @Description: 增强类 升降时分布式锁控制
 * @Date: 2019/6/14
 */
@Slf4j
public class LockLockDecorator extends LockDecorator implements Lock{

    private static final String luaLock = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private static final String  REDIS_CTL_LOCK = "REDIS:CTL:LOCK:";
    private volatile String sn;
    
    RedisService redisService = SpringUtil.getBean(RedisService.class);

    private Lock lock = new ReentrantLock();
    private Long userId;

    public LockLockDecorator(LockService lockService) {
        super(lockService);
    }

    @Override
    public Boolean downLock(String sn) throws CommonException {
    	try {
			lock();
			return super.downLock(sn);
    	}finally {
    		unlock();
		}
    }

    @Override
    public ResLockMessage downLockMes(String sn) throws CommonException {
        try {
        	lock();
        	return super.downLockMes(sn);
		}finally {
			unlock();
		}
    }

    @Override
    public boolean upLock(String sn) throws CommonException {
		try {
			lock();
			return super.upLock(sn);
		}finally {
			unlock();
		}
    }

    @Override
    public ResLockMessage upLockMes(String sn) throws CommonException {
       try {
    	   lock();
    	   return super.upLockMes(sn);
       }finally {
    	   unlock();
       }
    }

    @Override
    public void lock() {
        lock.lock();
        try {
            boolean b = redisLock(sn, userId);
            if(!b){
                throw  new CommonException(ResponseCodeEnum.CARPLACEOCCUPIED);
            }
        } catch (CommonException e) {
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        lock.lock();
        try {
            boolean b = redisUpLock(sn, userId);
            if(!b){
                throw new CommonException(ResponseCodeEnum.USER_NOT_UNLOCK_AUTH);
            }
        } catch (CommonException e) {
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /**
     * @Description  上锁  #TODO  未解决原子性操作问题  后期需解决
     * @Author   GFF 
     * @Version  v2.0
     */
    private boolean redisLock(String sn ,Long userId){
    	if(userId == null) {
    		return true;
    	}
    	log.info("锁编号上锁");
        String lock = REDIS_CTL_LOCK+sn;
        log.info("上锁code "+lock);
        Boolean falg = redisService.lock(lock, userId + "", null);
        if(falg){
            log.info("上锁成功");
            return falg;
        }
    	log.error("已经有别的用户获取锁");
        return false;
    }
    
    /**
     * @Description  解锁  #TODO  未解决原子性操作问题  后期需解决
     * @Author   GFF 
     * @Version  v2.0
     */
    private boolean redisUpLock(String sn ,Long userId){
    	if(userId == null) {
    		return true;
    	}
    	log.info("解锁锁编号");
    	String lock = REDIS_CTL_LOCK+sn;
        log.info("解锁锁编号code码 "+lock);
        Boolean upLock = redisService.upLock(lock, userId + "");
        if(upLock){
            log.info("锁已经被解除 操作成功");
            return true;
        }
        log.error("非上锁用户进行解锁");
        return false;
    }
    
    public synchronized void setUserId(Long userId) {
    	this.userId = userId;
    }
}
