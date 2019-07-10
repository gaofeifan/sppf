package com.linkmoretech.parking.service.impl;

import com.esotericsoftware.minlog.Log;
import com.linkmoretech.auth.common.util.AuthenticationTokenAnalysis;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.parking.entity.*;
import com.linkmoretech.parking.entity.ResGatewayDetails;
import com.linkmoretech.parking.enums.CarPlaceStatusEnum;
import com.linkmoretech.parking.enums.CarPlaceTypeEnum;
import com.linkmoretech.parking.enums.LineStatusEnum;
import com.linkmoretech.parking.enums.LockStatusEnum;
import com.linkmoretech.parking.service.CarParkService;
import com.linkmoretech.parking.service.CarPlaceService;
import com.linkmoretech.parking.service.LockOperateService;
import com.linkmoretech.parking.service.LockService;
import com.linkmoretech.parking.vo.request.CarParkLineRequest;
import com.linkmoretech.parking.vo.request.CarPlaceEditRequest;
import com.linkmoretech.parking.vo.request.LineStatusRquest;
import com.linkmoretech.parking.vo.request.LockOperateRequest;
import com.linkmoretech.parking.vo.request.ReqLockIntall;
import com.linkmoretech.parking.vo.response.*;
import com.linkmoretech.parking.vo.response.ResLockGatewayList;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: GFF
 * @Description: 所操作实现类
 * @Date: 2019/6/17
 */
@Service
public class LockOperateServiceImpl implements LockOperateService {


    @Resource
    private CarParkService carParkService;
    @Resource
    private CarPlaceService carPlaceService;
    private LockFactory lockFactory = LockFactory.getInstance();
    @Override
    public Boolean operate(Authentication authentication, LockOperateRequest lockOperate) throws CommonException {
//    	 AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
//    	Long userId = authenticationTokenAnalysis.getUserId();
        LockService lockService = lockFactory.getLockService(LockFactory.MANAGE, lockOperate.getMsgStatus(), Boolean.TRUE, null);
        if(lockOperate.getCarPlaceId() == null && lockOperate.getLockSn() == null){
            throw new CommonException(ResponseCodeEnum.STALL_NOT_EXIST);
        }
        if(lockOperate.getLockSn() == null){
            CarPlaceInfoResponse detail = this.carPlaceService.findDetail(lockOperate.getCarPlaceId());
            if(detail == null || detail.getLockCode() == null){
                throw new CommonException(ResponseCodeEnum.STALL_NOT_EXIST);
            }
            lockOperate.setLockSn(detail.getLockCode());

        }
        if(lockOperate.getState().intValue() == 1){
            return lockService.downLock(lockOperate.getLockSn());
        }{
            return lockService.upLock(lockOperate.getLockSn());
        }
    }


    @Override
    public Boolean bindGroup(Long preId, String serialNumber, HttpServletRequest request) throws CommonException {
        CarParkInfoResponse detail1 = null;
        try {
            detail1 = carParkService.findDetail(preId);
        } catch (CommonException e) {
            e.printStackTrace();
        }
        return lockFactory.getLockService().bindGroup(detail1.getLockGroupCode(), serialNumber);
    }

    @Override
    public Boolean unBindGroup(String groupCode, String serialNumber, HttpServletRequest request) throws CommonException {
        return lockFactory.getLockService().unbindGroup(groupCode, serialNumber);
    }

    @Override
    public List<ResGateway> findGatewayGroup(Long preId, HttpServletRequest request) {
        CarParkInfoResponse detail = null;
        try {
            detail = this.carParkService.findDetail(preId);
        } catch (CommonException e) {
            e.printStackTrace();
        }
        List<ResGatewayGroup> group = lockFactory.getLockService().getGatewayGroup(detail.getLockGroupCode());
        List<ResGateway> gatewayList = new ArrayList<>();
        if(group != null) {
            for (ResGatewayGroup resGatewayGroup : group) {
                gatewayList.add(new ResGateway(resGatewayGroup.getSerialNumber(),resGatewayGroup.getGatewayState()));
            }
        }
        return gatewayList;
    }

    @Override
    public com.linkmoretech.parking.vo.response.ResGatewayDetails getGatewayDetails(String serialNumber, HttpServletRequest request) {
        ResGatewayDetails gatewayDetails = lockFactory.getLockService().getGatewayDetails(serialNumber);
        com.linkmoretech.parking.vo.response.ResGatewayDetails details = new com.linkmoretech.parking.vo.response.ResGatewayDetails();
        details.setAgentName(gatewayDetails.getAgentName());
        details.setGroupCode(gatewayDetails.getGroupCode());
        details.setSerialNumber(gatewayDetails.getSerialNumber());
        details.setSimType(gatewayDetails.getSimType());
        details.setVersion(gatewayDetails.getGatewayVersion());
        details.setGatewayState(gatewayDetails.getGatewayState());
        details.setSignal(gatewayDetails.getSignal());
        List<ResLocksGateway> gateways = lockFactory.getLockService().getLocksGateway(serialNumber);
        if(StringUtils.isNotBlank(gatewayDetails.getGroupCode())) {
            CarPark carPark = this.carParkService.findByGateway(gatewayDetails.getGroupCode());
            if(carPark != null) {
                details.setPreId(carPark.getId());
                details.setPreName(carPark.getParkName());
            }
        }
        if(gateways != null && gateways.size() != 0) {
            List<CarPlace> list = null;

            if(details.getPreId() != null) {
                list = this.carPlaceService.findCarPlaceByParkIds(Arrays.asList(details.getPreId()) );
            }
            ResStallLock stallLock = null;
            for (ResLocksGateway gateway : gateways) {
                stallLock = new ResStallLock();
                stallLock.setBindFlag(gateway.getBindFlag());
                stallLock.setBattery(gateway.getElectricity());
                stallLock.setLockSn(gateway.getLockSerialNumber());
                stallLock.setSignal(gateway.getSignal());
                if(list != null) {
                    String lockSn = gateway.getLockSerialNumber() != null ? gateway.getLockSerialNumber().substring(4).toUpperCase() : gateway.getLockSerialNumber();
                    for (CarPlace carPlace : list) {
                        if(lockSn != null && lockSn.equalsIgnoreCase(carPlace.getLockCode())) {
                            stallLock.setStallId(carPlace.getId());
                            stallLock.setStallName(carPlace.getPlaceNo());
                            stallLock.setBindStallStatus(1);
                            break;
                        }
                    }
                }
                details.getStallLocks().add(stallLock);
            }
        }
        return details;
    }

    @Override
    public Boolean loadLock(HttpServletRequest request, String serialNumber) {
        return lockFactory.getLockService().loadAllLock(serialNumber);
    }

    @Override
    public Boolean restartGateway(HttpServletRequest request, String serialNumber) {
        return lockFactory.getLockService().restart(serialNumber);
    }

    @Override
    public Boolean unBindLock(String lockSn, String serialNumber, HttpServletRequest request) {
        return lockFactory.getLockService().unBindLock(serialNumber, lockSn);
    }

    @Override
    public Boolean removeLock(String serialNumber, HttpServletRequest request) {
        return lockFactory.getLockService().removeLock(serialNumber);
    }
    @Override
    public Boolean confirm(String serialNumber, HttpServletRequest request) {
        return lockFactory.getLockService().confirm(serialNumber);
    }

    @Override
    public List<ResLockGatewayList> findLockGateways(
            HttpServletRequest request, String lockSn, Long preId) {
        List<ResLockGatewayList> list =new ArrayList<>();
        try {
            CarParkInfoResponse detail = this.carParkService.findDetail(preId);
            List<com.linkmoretech.parking.entity.ResLockGatewayList> gatewayList = lockFactory.getLockService().getLockGatewayList(lockSn,detail.getLockGroupCode());
            ResLockGatewayList gateway = null;
            for (com.linkmoretech.parking.entity.ResLockGatewayList resLockGatewayList : gatewayList) {
                gateway = new ResLockGatewayList(resLockGatewayList.getGatewaySerialNumber());
                gateway.setBindFlag(resLockGatewayList.getBindFlag());
                list.add(gateway);
            }
        } catch (CommonException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean editLockBindGateway(HttpServletRequest request, String serialNumbers, String lockSn) {
        Boolean gateway = lockFactory.getLockService().batchBindGateway(lockSn, serialNumbers);
        return gateway;
    }

    @Override
    @Transactional
    public Boolean installLock(ReqLockIntall reqLockIntall, HttpServletRequest request) throws CommonException {
//        Long userId = getUserId(getUser(request));
        CarPlace stall = this.carPlaceService.findByPlaceNoAndParkIdAndFloorPlanId(reqLockIntall.getStallName(),reqLockIntall.getPreId(),reqLockIntall.getFloorId());
        if(stall != null) {
            if(stall.getLineStatus().intValue() != LineStatusEnum.OFFLINE.getCode().intValue()){
                throw new CommonException(ResponseCodeEnum.CAR_PLACE_NOT_DOWN);
            }
            if(stall.getLockCode() != null){
                throw new CommonException(ResponseCodeEnum.STALL_LOCK_CODE_EXIST);
            }
        }
        stall = insertLock(stall,reqLockIntall);
//            authUserStall(cu, stall.getId());
        notityLockTerrace(reqLockIntall);
        return true;
    }
    private void notityLockTerrace(ReqLockIntall reqLockIntall) {
        Map<String, Object> map = new TreeMap<>();
        map.put("serialNumber", reqLockIntall.getLockSn());
        map.put("name", reqLockIntall.getStallName());
        lockFactory.getLockService().setLockName(map);
    }
    private CarPlace insertLock(CarPlace stall, ReqLockIntall reqLockIntall) throws CommonException {
        if(stall != null){
            stall.setLockCode(reqLockIntall.getLockSn());
            CarPlaceEditRequest carPlaceEditRequest = new CarPlaceEditRequest();
            carPlaceEditRequest.setId(stall.getId());
            carPlaceEditRequest.setPlaceNo(reqLockIntall.getStallName());
            carPlaceEditRequest.setLockCode(stall.getLockCode());
            CarParkInfoResponse detail = this.carParkService.findDetail(stall.getParkId());
            if(detail != null) {
                stall.setParkName(detail.getParkName());
            }
            this.carPlaceService.edit(carPlaceEditRequest);
        }else{
            stall = new CarPlace();
            stall.setLockCode(reqLockIntall.getLockSn());
            stall.setParkId(reqLockIntall.getPreId());
            stall.setFloorPlanId(reqLockIntall.getFloorId());
            stall.setFloorPlanName(reqLockIntall.getFloor());
            stall.setPlaceNo(reqLockIntall.getStallName());
            stall.setLineStatus(LineStatusEnum.OFFLINE.getCode());
            stall.setPlaceStatus(CarPlaceStatusEnum.FREE.getCode());
            stall.setPlaceType(CarPlaceTypeEnum.TEMP_PLACE.getCode());
            stall.setLockStatus(LockStatusEnum.UP.getCode());
            CarParkInfoResponse detail = this.carParkService.findDetail(stall.getParkId());
            if(detail != null) {
                stall.setParkName(detail.getParkName());
            }
            this.carPlaceService.insert(stall);
            
        }
        return stall;

    }


    @Override
    public Boolean removeStallLock(Long stallId, HttpServletRequest request) throws CommonException {
        CarPlaceInfoResponse detail = this.carPlaceService.findDetail(stallId);
        if(detail == null || detail.getLockCode() == null) {
            throw new CommonException(ResponseCodeEnum.STALL_NOT_EXIST);
        }
        if(detail.getLineStatus().intValue() != LineStatusEnum.OFFLINE.getCode().intValue()){
            throw new CommonException(ResponseCodeEnum.CAR_PLACE_NOT_DOWN);
        }
        detail.setLockCode(null);
        this.carPlaceService.updateLockCode(detail.getId(),detail.getLockCode());
        lockFactory.getLockService().removeLock(detail.getLockCode());
//		this.stallMasterMapper.delete(stall.getId());
        return true;
    }

    @Override
    public ResSignalHistory lockSignalHistory(HttpServletRequest request, String sn) {
        if(sn.contains("0000")) {
            sn = sn.substring(4).toUpperCase();
        }
        return lockFactory.getLockService().lockSignalHistory(sn);
    }

    private Object getUser(HttpServletRequest request){
        return new Object();
    }
    private Long getUserId(Object object){
        return 1L;
    }


	@Override
	public Boolean editLineStatus(LineStatusRquest lineStatusRquest) throws CommonException {
		this.carPlaceService.updateLineStatus(lineStatusRquest.getState(),lineStatusRquest.getCarPlaceId());
		// TODO 缺少将下线原因保存
		return true;
	}


	@Override
	public Boolean reset(Long carPlaceId) {
		
		return true;
	}

    
    

}
