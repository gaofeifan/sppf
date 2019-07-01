package com.linkmoretech.parking.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bouncycastle.crypto.RuntimeCryptoException;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.parking.service.LockService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: GFF
 * @Description: 锁操作的工厂类
 * @Date: 2019/6/17
 */
public class LockFactory {

    public static final int MANAGE = 0;
    public static final int USER = 1;
    private LockFactory() {}
    public static LockFactory getInstance(){
        return LazyLockFactory.lockFactory;
    }
    
    /**
     * @Author GFF
     * @Description  server 0管理版  type  操作的状态（0直连 1 极光 2 socket） lock 是否使用分布式锁控制
     * @Date 2019/6/17
     */
    public LockService getLockService(int server,int type ,boolean isLock){
    	checkServer(server);
    	LockService lockService = getCHM(server);
    	if(isLock){
            lockService = new LockLockDecorator(lockService);
        }
        lockService = new PushMessageDecorator(lockService,type);
        return lockService;
    }

    private void checkServer(int server) {
    	switch (server) {
		case MANAGE:
			break;
		case USER:
			break;
		default:
			throw new RuntimeCryptoException("参数有误请确认");
		}
	}
	public LockService getLockService(){
        return getLockService(0,0,false);
    }
	
	private synchronized void init() {
		LazyLockFactory.chm.put(MANAGE, new ManageLockDecorator(LockServiceImpl.getInstance()));
		LazyLockFactory.chm.put(USER, new UserLockDecorator(LockServiceImpl.getInstance()));
	}
	
	private LockService getCHM(Integer server) {
		LockService lockService = LazyLockFactory.chm.get(server);
		if(lockService == null) {
			init();
			lockService = LazyLockFactory.chm.get(server);
		}
		return lockService;
	}

    /**
     * @author   GFF
     * @Date     2019年6月28日
     * @Version  v2.0
     */
    private static class LazyLockFactory{
        private static final LockFactory lockFactory = new LockFactory();
        //	如果出现需要往集合里面添加数据的时候  请使用并发集合
//        private static final Map<Integer, LockService> chm = new ConcurrentHashMap<>();
        private static final Map<Integer, LockService> chm = new HashMap<>();
	
        
    }
}
