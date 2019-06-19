package com.linkmoretech.parking.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.component.BindLockPlatformComponent;
import com.linkmoretech.parking.component.CarParkComponent;
import com.linkmoretech.parking.entity.CarPark;
import com.linkmoretech.parking.entity.CarPlace;
import com.linkmoretech.parking.entity.FloorPlan;
import com.linkmoretech.parking.enums.CarPlaceStatusEnum;
import com.linkmoretech.parking.enums.CarPlaceTypeEnum;
import com.linkmoretech.parking.enums.LineStatusEnum;
import com.linkmoretech.parking.enums.LockStatusEnum;
import com.linkmoretech.parking.repository.CarParkRepository;
import com.linkmoretech.parking.repository.CarPlaceRepository;
import com.linkmoretech.parking.repository.FloorPlanRepository;
import com.linkmoretech.parking.repository.LicensePlateRepository;
import com.linkmoretech.parking.service.CarPlaceService;
import com.linkmoretech.parking.vo.request.CarLockCreateRequest;
import com.linkmoretech.parking.vo.request.CarPlaceCreateRequest;
import com.linkmoretech.parking.vo.request.CarPlaceEditRequest;
import com.linkmoretech.parking.vo.response.CarPlaceEditResponse;
import com.linkmoretech.parking.vo.response.CarPlaceInfoResponse;
import com.linkmoretech.parking.vo.response.CarPlacePageResponse;
import com.linkmoretech.parking.vo.response.CarPlaceSelectedResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collections;
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

        /**
         * 验证车位编号是否存在
         * */
        List<String> carPlaceNoList = carPlaceCreateRequest.getCarLockCreateRequestList().stream()
                .map(CarLockCreateRequest::getPlaceNo).collect(Collectors.toList());

        List<CarPlace> exitCarPlaceList = carPlaceRepository.getAllByParkIdAndPlaceNoIn(carPlaceCreateRequest.getCarParkId(),
                carPlaceNoList);
        if (exitCarPlaceList != null && exitCarPlaceList.size() > 0)  {
            List<String> exitPlaceNos = exitCarPlaceList.stream().map(CarPlace::getPlaceNo).collect(Collectors.toList());
            throw new CommonException(ResponseCodeEnum.ERROR, "车位号 " + JSONObject.toJSONString(exitPlaceNos) + "已存在");
        }

        /**
         * 查询该车位分布已存在数
         * */
        long placeCount = carPlaceRepository.countByParkIdAndFloorPlanId(carPark.getId(), carPlaceCreateRequest.getFloorPlanId());

        if (placeCount + carPlaceCreateRequest.getCarLockCreateRequestList().size() > floorPlan.getPlaceNumber()) {
            long diffCount = floorPlan.getPlaceNumber() - placeCount;
            throw new CommonException(ResponseCodeEnum.ERROR, "当前车位分布层最多只能添加 " + diffCount + " 个车位");
        }
        /**
         * 验证车位分布数是否足够
         * */
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
        if (carPlace.getLineStatus().equals(LineStatusEnum.ONLINE.getCode())) {
            throw new CommonException(ResponseCodeEnum.ERROR, "车位已上线,不能编辑");
        }
        /**
         * 检测是否是长租车位 如果市长租车位则同样删除长租车位数据
         * */
        if (carPlace.getPlaceType().equals(CarPlaceTypeEnum.FIXED_PLACE.getCode())) {

            /**
             * 删除该长租车位对应车牌号
             * */
            licensePlateRepository.deleteAllByParkIdAndPlaceId(carPlace.getParkId(), carPlace.getId());
            CarPark carPark = carParkComponent.getCarPark(carPlace.getParkId());
            carParkComponent.updateCarParkType(carPark);
        }
        carPlaceRepository.deleteById(carPlaceId);
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
            /*if (!carPlace.getLockStatus().equals(LockStatusEnum.UP.getCode())) {
                throw new CommonException(ResponseCodeEnum.ERROR, "车位锁未处于空闲状态");
            }*/

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


}
