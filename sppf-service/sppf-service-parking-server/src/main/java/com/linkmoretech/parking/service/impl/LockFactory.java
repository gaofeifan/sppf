package com.linkmoretech.parking.service.impl;

import com.linkmoretech.parking.service.LockService;

/**
 * @Author: GFF
 * @Description: 锁操作的工厂类
 * 最外层使用了单例模式创建简单工厂类  工厂生产锁对象
 * 锁对象使用了装饰者模式对额外功能进行增强 ,
 * 消息推送使用策略模式由客户端决定使用那种推送
 * @Date: 2019/6/17
 */
public class LockFactory {

    public static final int MANAGE = 0;
    public static final int USER = 1;

    public static LockFactory getInstance(){
        return LazyLockFactory.lockFactory;
    }
    /**
     * @Author GFF
     * @Description  server 0管理版  type  操作的状态（0直连 1 极光 2 socket） lock 是否使用分布式锁控制
     * @Date 2019/6/17
     */
    public LockService getLockService(int server,int type ,boolean isLock){
        LockService lockService = new LockServiceImpl();
        if(isLock){
            lockService = new LockLockDecorator(lockService);
        }
        if(server == 0){
            lockService = new ManageLockDecorator(lockService);
        }else if(server == 1){
        }
        lockService = new PushMessageDecorator(lockService);
        return lockService;
    }

    public LockService getLockService(){
        return getLockService(0,0,false);
    }


    private static class LazyLockFactory{
        private static final LockFactory lockFactory = new LockFactory();
    }
}
