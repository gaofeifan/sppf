package com.linkmoretech.parking.service.impl;
import com.linkmoretech.parking.entity.*;
import com.linkmoretech.parking.service.LockService;
import com.linkmoretech.parking.vo.response.ResSignalHistory;

import java.util.List;
import java.util.Map;

/**
 * 装饰者类  每次需要新的一种
 * @author   GFF
 * @Date     2019年1月17日
 * @Version  v2.0
 */
public abstract class LockDecorator implements LockService{

	private LockService lockService;


	public LockDecorator(LockService lockService) {
		this.lockService = lockService;
	}

	@Override
	public ResLockInfo lockInfo(String lockSn) {
		return lockService.lockInfo(lockSn);
	}

	@Override
	public ResSignalHistory lockSignalHistory(String sn) {
		return lockService.lockSignalHistory(sn);
	}

	@Override
	public boolean upLock(String sn) {
		return lockService.upLock(sn);
	}

	@Override
	public ResLockMessage upLockMes(String sn) {
		return lockService.upLockMes(sn);
	}

	@Override
	public Boolean downLock(String sn) {
		return lockService.downLock(sn);
	}

	@Override
	public ResLockMessage downLockMes(String sn) {
		return lockService.downLockMes(sn);
	}

	@Override
	public List<ResLockInfo> lockListByGroupCode(String groupCode) {
		return lockService.lockListByGroupCode(groupCode);
	}

	@Override
	public void setLockName(Map<String, Object> map) {
		lockService.setLockName(map);
	}

	@Override
	public String saveGroup(String groupName, String cityCode, String cityName, Double longitude, Double latitude,
			Integer positionNum) {
		return lockService.saveGroup(groupName, cityCode, cityName, longitude, latitude, positionNum);
	}

	@Override
	public Boolean removeGroupCode(String groupCode) {
		return lockService.removeGroupCode(groupCode);
	}

	@Override
	public Boolean bindGroup(String groupCode, String serialNumber) {
		return lockService.bindGroup(groupCode, serialNumber);
	}

	@Override
	public Boolean unbindGroup(String groupCode, String serialNumber) {
		return lockService.unbindGroup(groupCode, serialNumber);
	}

	@Override
	public Boolean loadAllLock(String serialNumber) {
		return lockService.loadAllLock(serialNumber);
	}

	@Override
	public ResGatewayDetails getGatewayDetails(String serialNumber) {
		return lockService.getGatewayDetails(serialNumber);
	}

	@Override
	public List<ResUnBindLock> unBindLockList(String serialNumber) {
		return lockService.unBindLockList(serialNumber);
	}

	@Override
	public List<ResUnBindLock> bindLockList(String serialNumber) {
		return lockService.bindLockList(serialNumber);
	}

	@Override
	public Boolean bindLock(String gatewaySerialNumbe, String lockSerialNumber) {
		return lockService.bindLock(gatewaySerialNumbe, lockSerialNumber);
	}

	@Override
	public Boolean unBindLock(String gatewaySerialNumbe, String lockSerialNumber) {
		return lockService.unBindLock(gatewaySerialNumbe, lockSerialNumber);
	}

	@Override
	public List<ResGatewayGroup> getGatewayGroup(String groupCode) {
		return lockService.getGatewayGroup(groupCode);
	}

	@Override
	public List<ResLocksGateway> getLocksGateway(String serialNumber) {
		return lockService.getLocksGateway(serialNumber);
	}

	@Override
	public List<ResLockGatewayList> getLockGatewayList(String serialNumber,String groupCode) {
		return lockService.getLockGatewayList(serialNumber,groupCode);
	}

	@Override
	public Boolean restart(String serialNumber) {
		return lockService.restart(serialNumber);
	}

	@Override
	public Boolean batchBindGateway(String lockSerialNumber, String gatewaySerials) {
		return lockService.batchBindGateway(lockSerialNumber,gatewaySerials);
	}

	@Override
	public Boolean removeLock(String serialNumber) {
		return lockService.removeLock(serialNumber);
	}

	@Override
	public Boolean confirm(String serialNumber) {
		return lockService.confirm(serialNumber);
	}

	
}
