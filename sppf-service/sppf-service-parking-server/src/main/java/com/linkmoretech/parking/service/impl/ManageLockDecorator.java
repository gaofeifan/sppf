package com.linkmoretech.parking.service.impl;

import com.linkmoretech.parking.entity.ResLockMessage;
import com.linkmoretech.parking.service.LockService;

/**
 * @Author: GFF
 * @Description: 管理版操作类  处理管理版升降锁其他逻辑
 * @Date: 2019/6/17
 */
public class ManageLockDecorator extends LockDecorator{
    public ManageLockDecorator(LockService lockService) {
        super(lockService);
    }



}
