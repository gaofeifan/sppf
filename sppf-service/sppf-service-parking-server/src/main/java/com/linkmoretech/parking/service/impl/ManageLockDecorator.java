package com.linkmoretech.parking.service.impl;

import com.linkmoretech.common.util.SpringUtil;
import com.linkmoretech.parking.entity.CarPlace;
import com.linkmoretech.parking.entity.ResLockMessage;
import com.linkmoretech.parking.service.CarPlaceService;
import com.linkmoretech.parking.service.LockService;

/**
 * @Author: GFF
 * @Description: 管理版操作类  处理管理版升降锁业务
 * @Date: 2019/6/17
 */
public class ManageLockDecorator extends LockDecorator{
	
	private CarPlaceService carPlaceService = SpringUtil.getBean(CarPlaceService.class);
	
    public ManageLockDecorator(LockService lockService) {
        super(lockService);
    }

	@Override
	public boolean upLock(String sn) {
		boolean b = super.upLock(sn);
		if(b) {
			updateCarPlaceStatus(1, sn);
		}
		return b;
	}

	@Override
	public ResLockMessage upLockMes(String sn) {
		ResLockMessage mes = super.upLockMes(sn);
		if(mes.getCode() == 200) {
			updateCarPlaceStatus(1, sn);
		}
		return mes;
	}

	@Override
	public Boolean downLock(String sn) {
		boolean b = super.downLock(sn);
		if(b) {
			updateCarPlaceStatus(2, sn);
		}
		return b;
	}

	@Override
	public ResLockMessage downLockMes(String sn) {
		ResLockMessage mes = super.downLockMes(sn);
		if(mes.getCode() == 200) {
			updateCarPlaceStatus(2, sn);
		}
		return mes; 
	}
    
    
	// status 1升起  2 降下
    private void updateCarPlaceStatus(int status,String lockCode) {
    	CarPlace carPlace = this.carPlaceService.findByLockCode(lockCode);
    	if(status == 1) {
    		carPlace.setLockStatus(1);
    		carPlace.setPlaceStatus(1);
    	}else if(status == 2) {
    		carPlace.setLockStatus(2);
    	}
    	carPlaceService.updateLockStatusAndPlaceStatus(lockCode,carPlace.getLockStatus(),carPlace.getPlaceStatus());
    }



}
