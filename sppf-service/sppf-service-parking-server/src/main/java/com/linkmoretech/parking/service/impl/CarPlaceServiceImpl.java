package com.linkmoretech.parking.service.impl;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.component.BindLockPlatformComponent;
import com.linkmoretech.parking.component.CarParkComponent;
import com.linkmoretech.parking.entity.CarPark;
import com.linkmoretech.parking.entity.CarPlace;
import com.linkmoretech.parking.entity.FloorPlan;
import com.linkmoretech.parking.entity.ResLockInfo;
import com.linkmoretech.parking.enums.CarPlaceStatusEnum;
import com.linkmoretech.parking.enums.CarPlaceTypeEnum;
import com.linkmoretech.parking.enums.LineStatusEnum;
import com.linkmoretech.parking.enums.LockStatusEnum;
import com.linkmoretech.parking.repository.CarParkRepository;
import com.linkmoretech.parking.repository.CarPlaceRepository;
import com.linkmoretech.parking.repository.FloorPlanRepository;
import com.linkmoretech.parking.repository.LicensePlateRepository;
import com.linkmoretech.parking.service.CarPlaceService;
import com.linkmoretech.parking.service.LockService;
import com.linkmoretech.parking.vo.request.CarPlaceCreateRequest;
import com.linkmoretech.parking.vo.request.CarPlaceEditRequest;
import com.linkmoretech.parking.vo.request.CarPlaceListRequest;
import com.linkmoretech.parking.vo.request.LockOperateRequest;
import com.linkmoretech.parking.vo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @Author: alec
 * Description:
 * @date: 10:24 2019-05-08
 */
@Service
@Slf4j
public class CarPlaceServiceImpl implements CarPlaceService {
    LockFactory lockFactory = LockFactory.getInstance();
    @Autowired
    CarPlaceRepository carPlaceRepository;

    @Autowired
    CarParkRepository carParkRepository;

    @Autowired
    FloorPlanRepository floorPlanRepository;

    @Autowired
    LicensePlateRepository licensePlateRepository;

    @Autowired
    BindLockPlatformComponent bindLockPlatformComponent;

    @Autowired
    CarParkComponent carParkComponent;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(CarPlaceCreateRequest carPlaceCreateRequest) throws CommonException {

        CarPark carPark = carParkComponent.getCarPark(carPlaceCreateRequest.getCarParkId());
        FloorPlan floorPlan = floorPlanRepository.findById(carPlaceCreateRequest.getFloorPlanId()).get();
        if (floorPlan.getId() == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "车场分布不存在");
        }
        Date currentDate = new Date();
        CarPlace carPlace = new CarPlace();
        carPlace.setParkId(carPark.getId());
        carPlace.setParkName(carPark.getParkName());
        carPlace.setFloorPlanId(floorPlan.getId());
        carPlace.setFloorPlanName(floorPlan.getFloorName());
        carPlace.setLineStatus(LineStatusEnum.OFFLINE.getCode());
        carPlace.setPlaceStatus(CarPlaceStatusEnum.FREE.getCode());
        carPlace.setPlaceType(CarPlaceTypeEnum.TEMP_PLACE.getCode());
        carPlace.setCreateTime(currentDate);
        carPlace.setUpdateTime(currentDate);
        carPlace.setChargeCode(carPark.getChargeCode());
        carPlace.setChargeName(carPark.getChargeName());
        carPlace.setCreateBy(carPlaceCreateRequest.getUsername());
        carPlace.setUpdateBy(carPlaceCreateRequest.getUsername());
        List<CarPlace> carPlaceList = carPlaceCreateRequest.getCarLockCreateRequestList().stream()
                .map(carLockCreateRequest -> {
            CarPlace tempCarPlace = new CarPlace();
            BeanUtils.copyProperties(carPlace, tempCarPlace);
            /**
             * 调用锁服务获取锁状态信息
             * */
            tempCarPlace.setLockCode(carLockCreateRequest.getLockCode());
            tempCarPlace.setPlaceNo(carLockCreateRequest.getPlaceNo());
            return tempCarPlace;
        }).collect(Collectors.toList());
       List<CarPlace> resultData = carPlaceRepository.saveAll(carPlaceList);
        log.info("save success and total is {}", resultData.size());
    }

    @Override
    public PageDataResponse<CarPlacePageResponse> searchPage(PageSearchRequest pageSearchRequest) {
        Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest
                .getPageSize(), Sort.by(Sort.Order.asc("parkId")));

        Page<CarPlace> carPlaces = carPlaceRepository.findAll(pageable);
        PageDataResponse<CarPlacePageResponse> pageDataResponse = new PageDataResponse<>();
        pageDataResponse.setTotal(carPlaces.getTotalElements());
        List<CarPlacePageResponse> carPlacePageResponses = carPlaces.getContent().stream()
                .map(carPlace -> {
                    CarPlacePageResponse carPlacePageResponse = new CarPlacePageResponse();
                    BeanUtils.copyProperties(carPlace, carPlacePageResponse);
                    return carPlacePageResponse;
                }).collect(Collectors.toList());
        pageDataResponse.setData(carPlacePageResponses);
        return pageDataResponse;
    }

    @Override
    public List<CarPlaceSelectedResponse> getSelectedData(Long carParkId) {
        List<CarPlace> carPlaceList = carPlaceRepository
                .getAllByParkIdAndPlaceTypeAndLineStatusAndPlaceStatus(carParkId, CarPlaceTypeEnum.TEMP_PLACE.getCode(),
                        LineStatusEnum.ONLINE.getCode(), CarPlaceStatusEnum.FREE.getCode());
        List<CarPlaceSelectedResponse> carPlaceSelectedResponses = carPlaceList.stream().map(carPlace -> {
            CarPlaceSelectedResponse carPlaceSelectedResponse = new CarPlaceSelectedResponse();
            carPlaceSelectedResponse.setId(carPlace.getId());
            carPlaceSelectedResponse.setPlaceNo(carPlace.getPlaceNo());
            return carPlaceSelectedResponse;
        }).collect(Collectors.toList());
        return carPlaceSelectedResponses;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(Long carPlaceId) throws CommonException {
        CarPlace carPlace = carParkComponent.getCarPlace(carPlaceId);
        /**
         * 检测是否是长租车位 如果市长租车位则同样删除长租车位数据
         * */
        if (carPlace.getPlaceType().equals(CarPlaceTypeEnum.FIXED_PLACE.getCode())) {

            /**
             * 删除该长租车位对应车牌号
             * */
            licensePlateRepository.deleteAllByParkIdAndPlaceId(carPlace.getParkId(), carPlace.getId());
            CarPark carPark = carParkRepository.findById(carPlace.getParkId()).get();
            carParkComponent.updateCarParkType(carPark);
        }
        carPlaceRepository.deleteById(carPlace.getId());
    }

    @Override
    public CarPlaceInfoResponse findDetail(Long carPlaceId) throws CommonException {
        CarPlace carPlace = carParkComponent.getCarPlace(carPlaceId);
        CarPlaceInfoResponse carPlaceInfoResponse = new CarPlaceInfoResponse();
        BeanUtils.copyProperties(carPlace, carPlaceInfoResponse);
        if (carPlace.getPlaceType().equals(CarPlaceTypeEnum.FIXED_PLACE.getCode())) {
            /**
             * 长租车位，需要获取长租车位信息
             * */
            log.info("查询长租车位信息，获取长租车位信息");
        }
        return carPlaceInfoResponse;
    }

    @Override
    public CarPlaceEditResponse findEditInfo(Long carPlaceId) throws CommonException {
        CarPlace carPlace = carParkComponent.getCarPlace(carPlaceId);
        if (carPlace.getLineStatus().equals(LineStatusEnum.ONLINE.getCode())) {
            throw new CommonException(ResponseCodeEnum.ERROR, "车位已上线,不能编辑");
        }
        return new CarPlaceEditResponse(carPlace.getId(), carPlace.getPlaceNo(), carPlace.getLockCode());
    }

    @Override
    public void edit(CarPlaceEditRequest carPlaceEditRequest) throws CommonException {
        CarPlace carPlace = carParkComponent.getCarPlace(carPlaceEditRequest.getId());
        if (carPlace.getLineStatus().equals(LineStatusEnum.ONLINE.getCode())) {
            throw new CommonException(ResponseCodeEnum.ERROR, "车位已上线,不能编辑");
        }
        carPlace.setPlaceNo(carPlaceEditRequest.getPlaceNo());
        carPlace.setLockCode(carPlaceEditRequest.getLockCode());
        carPlace.setUpdateTime(new Date());
        carPlace.setUpdateBy(carPlaceEditRequest.getUsername());
        carPlaceRepository.save(carPlace);
    }

    @Override
    public void upDownLine(Long id, Integer lineStatus) throws CommonException {
        CarPlace carPlace = carParkComponent.getCarPlace(id);
        LineStatusEnum lineStatusEnum = LineStatusEnum.getStatus(lineStatus);
        if (lineStatusEnum == null) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        if (lineStatus.equals(LineStatusEnum.ONLINE.getCode())) {
            /**
             * 1 调用锁服务获取锁信息
             * 2 对锁进行升起操作
             * 3 将锁加入可用队列
             * 4 将锁加入观察者队列
             * */
            bindLockPlatformComponent.getLockInfo(carPlace);
            if (!carPlace.getLockStatus().equals(LockStatusEnum.UP.getCode())) {
                throw new CommonException(ResponseCodeEnum.ERROR, "车位锁未处于空闲状态");
            }

        }
        if (lineStatus.equals(LineStatusEnum.OFFLINE.getCode())) {
            /**
             * 1 设置锁对状态为空
             * 2 从可用队列移除
             * 3 清楚观察者队列
             * */
        }
        carPlace.setLineStatus(lineStatusEnum.getCode());
        carPlace.setUpdateTime(new Date());
        carPlaceRepository.save(carPlace);
    }

    @Override
    public List<CarPlace> findCarPlaceByParkIds(List<Long> parkIds) {
        return  carPlaceRepository.getAllByParkIdIn(parkIds);
    }

    @Override
    public CarPlace findByPlaceNoAndParkId(String stallName, Long preId) {
        return this.carPlaceRepository.findByPlaceNoAndParkId(stallName,preId);
    }

    @Override
    public void insert(CarPlace stall) {
        this.carPlaceRepository.save(stall);
    }

    @Override
    public void updateLockCode(Long id, String lockCode) {
        this.carPlaceRepository.updateLockCode(id,lockCode);
    }

    @Override
    public List<CarPalceListResponse> findCarPlaceListByParkId(HttpServletRequest request, CarPlaceListRequest carPlaceListRequest) {
        CarPlace carPlaceQuery = new CarPlace();
        carPlaceQuery.setParkId(carPlaceListRequest.getCarParkId());
        Example<CarPlace> of = Example.of(carPlaceQuery);
        List<CarPlace> carPlaceList = this.carPlaceRepository.findAll(of);
//        List<CarPlace> carPlaceList = this.carPlaceRepository.getAllByParkIdAndLike(carPlace);
        List<CarPalceListResponse> carPalceListResponses = new ArrayList<>();
        CarPalceListResponse carPalceListResponse;
        CarPark carPark = this.carParkRepository.findById(carPlaceListRequest.getCarParkId()).get();
        List<ResLockInfo> lockInfos = this.lockFactory.getLockService().lockListByGroupCode(carPark.getLockGroupCode());
        for (CarPlace carPlace:carPlaceList ) {
            carPalceListResponse = new CarPalceListResponse();
            carPalceListResponse.setId(carPlace.getId());
            carPalceListResponse.setCarPalceName(carPlace.getPlaceNo());
            carPalceListResponse.setLockStatus(carPlace.getLockStatus());
            carPalceListResponse.setPlaceStatus(carPlace.getPlaceStatus());
            carPalceListResponse.setType(carPlace.getPlaceType());
            carPalceListResponse.setLockSn(carPlace.getLockCode());
            carPalceListResponse.setPlaceStatus(carPlace.getPlaceStatus());
            carPalceListResponse = setCarPlaceLock(lockInfos,carPalceListResponse);
            carPalceListResponses.add(carPalceListResponse);
        }
        return carPalceListResponses;
    }

    private CarPalceListResponse setCarPlaceLock(List<ResLockInfo> lockInfos ,CarPalceListResponse carPalceListResponse){
        for (ResLockInfo lockInfo : lockInfos){
            if(lockInfo.getLockCode().equals(carPalceListResponse.getLockSn())){
                if(lockInfo.getElectricity() <= 30) {
                    carPalceListResponse.setExcStatus(false);
                }
                switch (lockInfo.getLockState()) {
                    case 0:
                        carPalceListResponse.setLockStatus(2);
                        break;
                    case 2:
                        carPalceListResponse.setLockStatus(1);
                        break;
                    case 3:
                        carPalceListResponse.setLockStatus(2);
                        break;
                    case 1:
                        carPalceListResponse.setLockStatus(1);
                        break;
                }
            }
        }
        return carPalceListResponse;
    }

    @Override
    public CarPlaceDetailsResponse details(HttpServletRequest request, Long carPlaceId) {
        CarPlace carPlace = this.carPlaceRepository.findById(carPlaceId).get();
        CarPlaceDetailsResponse details = new CarPlaceDetailsResponse();
        if(carPlace != null){
            details.setCarPlaceId(carPlace.getId());
//            details.setBetty();
//            details.setCarStatus();
            details.setFloor(carPlace.getFloorPlanName());
            details.setLockSn(carPlace.getLockCode());
            details.setCarPlaceName(carPlace.getPlaceNo());
            details.setLockStatus(carPlace.getLockStatus());
            details.setCarStatus(carPlace.getPlaceStatus());
            details.setLineStatus(carPlace.getLineStatus());
            ResLockInfo lockInfo = this.lockFactory.getLockService().lockInfo(carPlace.getLockCode());
            details = setDetailsLockInfo(lockInfo , details);
        }
        return details;
    }

    private CarPlaceDetailsResponse setDetailsLockInfo(ResLockInfo lockBean, CarPlaceDetailsResponse detail) {
        if (lockBean != null) {
            detail.setBetty(lockBean.getElectricity());
            detail.setCarStatus(lockBean.getParkingState());
            switch (lockBean.getLockState()) {
                case 0:
                    detail.setLockStatus(2);
                    break;
                case 2:
                    detail.setLockStatus(1);
                    break;
                case 3:
                    detail.setLockStatus(2);
                    break;
                case 1:
                    detail.setLockStatus(1);
                    break;
            }
        } else {
            detail.setBetty(0);
        }
        return detail;
    }

    @Override
    public CarPlaceDetailsSnResponse detailsSn(HttpServletRequest request, String sn, Long parkId) {
        if(sn.contains("0000")) {
            sn = sn.substring(4).toUpperCase();
        }
        CarPlace carPlace = this.carPlaceRepository.getOneByLockCodeAndParkId(sn,parkId);
        CarPlaceDetailsSnResponse carPlaceRes = new CarPlaceDetailsSnResponse();
        carPlaceRes.setCarPlaceLockSn(sn);
        LockService lockService = this.lockFactory.getLockService();
        ResLockInfo lockInfo = lockService.lockInfo(carPlace.getLockCode());
        if(carPlace != null){
            carPlaceRes.setCarPlaceId(carPlace.getId());
            carPlaceRes.setParkId(carPlace.getParkId());
            carPlaceRes.setParkName(carPlace.getParkName());
            carPlaceRes.setCarPlaceStatus(carPlace.getPlaceStatus());
            carPlaceRes.setLockStatus(carPlace.getLockStatus());
            carPlaceRes.setFloor(carPlace.getFloorPlanName());
            carPlaceRes.setCarPlaceLockSn(sn);
        }
        if(lockInfo != null){
            carPlaceRes.setBindStata(2);
            carPlaceRes.setBindStatus(true);
            carPlaceRes.setInstallStatus((short)1);
            carPlaceRes.setLockOffLine(lockInfo.getOnlineState());
            carPlaceRes.setBattery(lockInfo.getElectricity());
            carPlaceRes.setUltrasonic(lockInfo.getParkingState());
            carPlaceRes.setInductionState(lockInfo.getInductionState());
            carPlaceRes.setModel(lockInfo.getModel());
            carPlaceRes.setVersion(lockInfo.getVersion());
            CarPark park = this.carParkRepository.findById(carPlace.getId()).get();
            if(park != null){
                carPlaceRes.setCityCode(park.getCityCode());
                carPlaceRes.setCityName(park.getCityName());
                List<com.linkmoretech.parking.entity.ResLockGatewayList> gatewayList = lockService.getLockGatewayList(carPlace.getLockCode(), park.getLockGroupCode());
                com.linkmoretech.parking.vo.response.ResLockGatewayList rgl = null;
                if(gatewayList != null) {
                    for (com.linkmoretech.parking.entity.ResLockGatewayList resLockGatewayList : gatewayList) {
                        if(resLockGatewayList.getBindFlag().equals("1")) {
                            rgl = new com.linkmoretech.parking.vo.response.ResLockGatewayList(resLockGatewayList.getGatewaySerialNumber());
                            rgl.setBindFlag(resLockGatewayList.getBindFlag());
                            carPlaceRes.getGatewayList().add(rgl);
                        }
                    }
                }
            }
        }
        return carPlaceRes;
    }




}
