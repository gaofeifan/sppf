package com.linkmoretech.parking.service.impl;

import com.linkmoretech.parking.service.LockService;

/**
 * 个人版锁操作的具体实现类
 * @author   GFF
 * @Date     2019年6月28日  
 * @Version  v2.0
 */
public class UserLockDecorator extends LockDecorator {

	public UserLockDecorator(LockService lockService) {
		super(lockService);
	}

}
