package com.linkmoretech.parking.service.impl;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.util.TaskPool;
import com.linkmoretech.parking.entity.ResLockMessage;
import com.linkmoretech.parking.enums.PushType;
import com.linkmoretech.parking.service.LockService;
import com.linkmoretech.parking.service.PushService;


/**
 * @Author: GFF
 * @Description: 操作锁消息推送类
 * @Date: 2019/6/17
 */
public class PushMessageDecorator extends LockDecorator implements PushService {
    public PushMessageDecorator(LockService lockService) {
        super(lockService);
    }
    PushType[] values = PushType.values();
    private int type = 0;
    @Override
    public boolean upLock(String sn) throws CommonException {

        Boolean lock = super.upLock(sn);
        Boolean pushFlag = this.push();
        return lock;
    }

    @Override
    public ResLockMessage upLockMes(String sn) throws CommonException {
        ResLockMessage message = super.upLockMes(sn);
        Boolean pushFlag = this.push();
        return message;
    }

    public PushMessageDecorator(LockService lockService, int type) {
        super(lockService);
        synchronized (this) {
            this.type = type;
        }
    }

    @Override
    public Boolean downLock(String sn) throws CommonException {
        Boolean lock = super.downLock(sn);
        this.push();
        return lock;
    }

    @Override
    public ResLockMessage downLockMes(String sn) throws CommonException {
        ResLockMessage lockMessage = super.downLockMes(sn);
        this.push();
        return lockMessage;
    }

    @Override
    public Boolean push() {
        TaskPool.getInstance().task(new Runnable() {
            @Override
            public void run() {
                for (PushType p:values ) {
                    if(type == p.getIndex()){
                        p.get().push();
                    }
                }
            }
        });
        return true;
    }

    public synchronized void setType(int type){
        this.type = type;
    }
}
