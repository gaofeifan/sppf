package com.linkmoretech.parking.service.impl;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.parking.entity.ResLockMessage;
import com.linkmoretech.parking.service.LockService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: GFF
 * @Description: 增强类 升降时分布式锁控制
 * @Date: 2019/6/14
 */
public class LockLockDecorator extends LockDecorator implements Lock{

    private static final String luaLock = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private volatile String sn;
    
    private RedisTemplate redisTemplate;

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
            boolean b = redisLock(sn, userId);
            if(!b){
                throw new CommonException(ResponseCodeEnum.CARPLACEOCCUPIED);
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

    private boolean redisLock(String sn ,Long userId){
        return true;
    }
    
    private boolean redisUpLock(String sn ,Long userId){
        return true;
    }
}
