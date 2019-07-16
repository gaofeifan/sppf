package com.linkmoretech.parking.service.impl;

import com.linkmoretech.account.client.AccountDataClient;
import com.linkmoretech.auth.common.util.AuthenticationTokenAnalysis;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.util.JsonUtil;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.component.BindLockPlatformComponent;
import com.linkmoretech.parking.component.CarParkComponent;
import com.linkmoretech.parking.entity.CarPark;
import com.linkmoretech.parking.entity.CarPlace;
import com.linkmoretech.parking.entity.Coordinate;
import com.linkmoretech.parking.entity.FloorPlan;
import com.linkmoretech.parking.enums.CoordinateTypeEnum;
import com.linkmoretech.parking.enums.LineStatusEnum;
import com.linkmoretech.parking.enums.CarParkTypeEnum;
import com.linkmoretech.parking.repository.CarParkRepository;
import com.linkmoretech.parking.repository.CarPlaceRepository;
import com.linkmoretech.parking.repository.CoordinateRepository;
import com.linkmoretech.parking.repository.FloorPlanRepository;
import com.linkmoretech.parking.service.CarParkService;
import com.linkmoretech.parking.service.CarPlaceService;
import com.linkmoretech.parking.vo.request.CarParkCreateRequest;
import com.linkmoretech.parking.vo.request.CarParkEditRequest;
import com.linkmoretech.parking.vo.request.CarParkLineRequest;
import com.linkmoretech.parking.vo.request.FloorPlanRequest;
import com.linkmoretech.parking.vo.response.*;
import com.linkmoretech.versatile.client.AreaCityClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @Author: alec
 * @Description:
 * @date: 4:22 PM 2019/5/5
 */
@Service
@Slf4j
public class CarParkServiceImpl implements CarParkService {

    @Autowired
	AccountDataClient accountDataClient;
	@Autowired
    CarPlaceService carPlaceService;
    @Autowired
    CarParkRepository carParkRepository;
    @Autowired
    FloorPlanRepository floorPlanRepository;
    @Autowired
    CoordinateRepository coordinateRepository;
    @Autowired
    AreaCityClient areaCityClient;
    @Autowired
    BindLockPlatformComponent bindLockPlatformComponent;
    @Autowired
    CarParkComponent carParkComponent;

    @Autowired
    CarPlaceRepository carPlaceRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(CarParkCreateRequest carParkCreateRequest) throws CommonException {
        Date currentDate = new Date();
        CarPark carPark = new CarPark();
        BeanUtils.copyProperties(carParkCreateRequest, carPark);
        String cityName = areaCityClient.getCityName(carParkCreateRequest.getCityCode());
        carPark.setCityName(cityName);
        carPark.setParkType(CarParkTypeEnum.TEMPORARY.getCode());
        carPark.setFixedStatus(LineStatusEnum.OFFLINE.getCode());
        carPark.setTempStatus(LineStatusEnum.OFFLINE.getCode());
        carPark.setCreateTime(currentDate);
        carPark.setUpdateTime(currentDate);
        carPark.setCreateBy(carParkCreateRequest.getUsername());
        carPark.setUpdateBy(carParkCreateRequest.getUsername());
        CarPark resultCarPark = carParkRepository.save(carPark);
        if (resultCarPark.getId() == null) {
            throw new CommonException(ResponseCodeEnum.ERROR);
        }
        List<FloorPlanRequest> floorPlanRequests = carParkCreateRequest.getFloorPlanRequests();
        saveOrUpdateFloorPlan(floorPlanRequests, resultCarPark.getId(), currentDate);
        List<Coordinate> coordinateList = saveOrUpdateMap(resultCarPark.getId(), carParkCreateRequest.getBaiduMap(), carParkCreateRequest.getGaodeMap());
        String groupCode = bindLockPlatformComponent.bindGroup(carPark, coordinateList.get(0));
        resultCarPark.setLockGroupCode(groupCode);
        carParkRepository.save(resultCarPark);
    }
    @Override
    public PageDataResponse<CarParkPageResponse> searchPage(PageSearchRequest pageSearchRequest) {
        Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest.getPageSize());
        Page<CarPark> carParkPage = carParkRepository.findAll(pageable);
        PageDataResponse<CarParkPageResponse> pageDataResponse = new PageDataResponse<>();
        pageDataResponse.setTotal(carParkPage.getTotalElements());
        List<CarPark> carParks = carParkPage.getContent();

        Map<Long, List<FloorPlan>> floorPlanMap = getFloorPlanForCarParkList(carParks);
        List<CarParkPageResponse> carParkPageResponses = carParks.stream().map(carPark -> {
            CarParkPageResponse carParkPageResponse = new CarParkPageResponse();
            BeanUtils.copyProperties(carPark, carParkPageResponse);
            List<FloorPlan> floorPlanList = floorPlanMap.get(carPark.getId());
            List<FloorPlanResponse> floorPlanResponseList = floorPlanList.stream().map(floorPlan -> {
                FloorPlanResponse floorPlanResponse = new FloorPlanResponse(floorPlan.getId(), floorPlan.getFloorName(), floorPlan.getPlaceNumber());
                return floorPlanResponse;
            }).collect(Collectors.toList());
            carParkPageResponse.setFloorPlanResponseList(floorPlanResponseList);
            return carParkPageResponse;
        }).collect(Collectors.toList());
        pageDataResponse.setData(carParkPageResponses);
        return pageDataResponse;
    }
    @Override
    public CarParkInfoResponse findDetail(Long carParkId) throws CommonException {
        CarPark carPark = carParkComponent.getCarPark(carParkId);
        CarParkInfoResponse carParkInfoResponse = new CarParkInfoResponse();
        BeanUtils.copyProperties(carPark, carParkInfoResponse);
        List<FloorPlanResponse> floorPlanResponses = getFloorPlanResponse(carParkId);
        carParkInfoResponse.setFloorPlanResponseList(floorPlanResponses);
        return carParkInfoResponse;
    }
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(Long carParkId) throws CommonException {
        CarPark carPark = carParkRepository.findById(carParkId).get();
        if (carPark.getId() == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "未找到相关车场");
        }
        if (carPark.getFixedStatus().equals(LineStatusEnum.ONLINE.getCode())) {
            throw new CommonException(ResponseCodeEnum.ERROR, "请先下线固定车位");
        }
        carParkRepository.deleteById(carParkId);
        List<FloorPlan> removeFloorPlan = floorPlanRepository.deleteAllByParkId(carParkId);
        if (removeFloorPlan.size() == 0) {
            throw new CommonException(ResponseCodeEnum.FAILURE);
        }
        coordinateRepository.deleteAllByCarParkId(carParkId);
        /**
         * 删除车位数据
         * */
        floorPlanRepository.deleteAllByParkId(carParkId);
        /**
         * 删除长租车位数据
         * */
        carPlaceRepository.deleteAllByParkId(carParkId);
    }
    @Override
    public List<CarParkSelectResponse> getSelectedData(String cityCode)  {
        List<CarPark> carParks = carParkRepository.findAllByCityCode(cityCode);
        Map<Long, List<FloorPlan>> floorPlanMap = getFloorPlanForCarParkList(carParks);
        return carParks.stream().map(carPark -> {
            CarParkSelectResponse carParkSelectResponse = new CarParkSelectResponse();
            carParkSelectResponse.setId(carPark.getId());
            carParkSelectResponse.setParkName(carPark.getParkName());
            List<FloorPlanResponse> floorPlanResponses = floorPlanMap.get(carPark.getId()).stream().map(floorPlan -> {
                FloorPlanResponse floorPlanResponse = new FloorPlanResponse();
                BeanUtils.copyProperties(floorPlan, floorPlanResponse);
                return floorPlanResponse;
            }).collect(Collectors.toList());
            log.info("floorPlan {}", floorPlanResponses.size());
            carParkSelectResponse.setFloorPlanResponseList(floorPlanResponses);
            return carParkSelectResponse;
        }).collect(Collectors.toList());
    }
    @Override
    public CarParkEditResponse findEditInfo(Long carParkId) throws CommonException {
        CarPark carPark = carParkComponent.getCarPark(carParkId);
        CarParkEditResponse carParkEditResponse = new CarParkEditResponse();
        BeanUtils.copyProperties(carPark, carParkEditResponse);
        List<FloorPlanResponse> floorPlanResponses = getFloorPlanResponse(carParkId);
        carParkEditResponse.setFloorPlanResponses(floorPlanResponses);
        List<String> cityCodes = areaCityClient.getAllCityCode(carPark.getCityCode());
        carParkEditResponse.setCityCodes(cityCodes);
        List<Coordinate> coordinateList = coordinateRepository.getAllByCarParkId(carParkId);
        coordinateList.forEach(coordinate -> {
            if (coordinate.getType().equals(CoordinateTypeEnum.BAI_DU_MAP.getCode())) {
                String baiduMap = coordinate.getLatitude().toString() + ',' + coordinate.getLongitude().toString();
                carParkEditResponse.setBaiduMap(baiduMap);
            }
            if (coordinate.getType().equals(CoordinateTypeEnum.GAO_DE_MAP.getCode())) {
                String gaodeMap = coordinate.getLatitude().toString() + ',' + coordinate.getLongitude().toString();
                carParkEditResponse.setGaodeMap(gaodeMap);
            }
        });
        return carParkEditResponse;
    }
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void edit(CarParkEditRequest carParkEditRequest) throws CommonException {
        Date currentDate = new Date();
        CarPark carPark = carParkComponent.getCarPark(carParkEditRequest.getId());
        BeanUtils.copyProperties(carParkEditRequest, carPark);
        carPark.setUpdateTime(currentDate);
        String cityName = areaCityClient.getCityName(carParkEditRequest.getCityCode());
        carPark.setCityName(cityName);
        carPark.setUpdateBy(carParkEditRequest.getUsername());
        carParkRepository.save(carPark);
        List<FloorPlanRequest> floorPlanRequests = carParkEditRequest.getFloorPlanRequests();
        saveOrUpdateFloorPlan(floorPlanRequests, carPark.getId(), currentDate);
        saveOrUpdateMap(carPark.getId(), carParkEditRequest.getBaiduMap(), carParkEditRequest.getGaodeMap());
    }
    @Override
    public void upDownLine(CarParkLineRequest carParkLineRequest) throws CommonException {
        CarPark carPark = carParkComponent.getCarPark(carParkLineRequest.getId());
        LineStatusEnum lineStatusEnum = LineStatusEnum.getStatus(carParkLineRequest.getLineStatus());
        if (lineStatusEnum == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "状态码错误");
        }
        if (carParkLineRequest.getLineType().equals(CarParkTypeEnum.TEMPORARY.getCode())) {
            carPark.setTempStatus(lineStatusEnum.getCode());
        }
        if (carParkLineRequest.getLineType().equals(CarParkTypeEnum.RENTAL.getCode())) {
            carPark.setFixedStatus(lineStatusEnum.getCode());
        }
        carPark.setUpdateTime(new Date());
        carPark.setUpdateBy(carParkLineRequest.getUsername());
        carParkRepository.save(carPark);
    }
    /**
     * 根据车场列表查询对应记录锁关联的车位分布分组数据
     * */
    private Map<Long, List<FloorPlan>> getFloorPlanForCarParkList(List<CarPark> carParkList) {
        List<Long> carParkIdList = carParkList.stream().map(CarPark :: getId).collect(Collectors.toList());
        List<FloorPlan> floorPlanList = floorPlanRepository.getAllByParkIdIn(carParkIdList);
        return floorPlanList.stream().collect(Collectors.groupingBy(FloorPlan :: getParkId));
    }
    private List<FloorPlanResponse> getFloorPlanResponse(Long carParkId) {
        List<FloorPlan> floorPlanList = floorPlanRepository.getAllByParkId(carParkId);
        return floorPlanList.stream().map(floorPlan -> {
            FloorPlanResponse floorPlanResponse = new FloorPlanResponse(floorPlan.getId(), floorPlan.getFloorName(),
                    floorPlan.getPlaceNumber());
            return floorPlanResponse;
        }).collect(Collectors.toList());
    }
    private Coordinate getCoordinate(Long carParkId, String mapPoints, Integer type) {
        String regex = ",";
        String[] points = mapPoints.split(regex);
        Coordinate coordinate = new Coordinate();
        coordinate.setCarParkId(carParkId);
        coordinate.setLatitude(new BigDecimal(points[0]));
        coordinate.setLongitude(new BigDecimal(points[1]));
        coordinate.setType(type);
        return coordinate;
    }
    /**
     * 更新插入楼层信息
     * */
    private void saveOrUpdateFloorPlan(List<FloorPlanRequest> floorPlanRequests, Long carParkId, Date currentDate){
        /**
         * 删除已有数据
         * */
        floorPlanRepository.deleteAllByParkId(carParkId);
        List<FloorPlan> floorPlanList = floorPlanRequests.stream().map(floorPlanRequest -> {
            FloorPlan floorPlan = new FloorPlan();
            floorPlan.setFloorName(floorPlanRequest.getFloorName());
            floorPlan.setPlaceNumber(floorPlanRequest.getPlaceNumber());
            floorPlan.setCreatedTime(currentDate);
            floorPlan.setUpdatedTime(currentDate);
            floorPlan.setParkId(carParkId);
            return floorPlan;
        }).collect(Collectors.toList());
        if (floorPlanList.size() > 0) {
            floorPlanRepository.saveAll(floorPlanList);
        }
    }
    /**
     * 更新插入坐标信息
     * */
    private List<Coordinate> saveOrUpdateMap(Long carParkId, String baiduMap, String gaoduMap) {
        /**
         * 删除已有数据
         * */
        coordinateRepository.deleteAllByCarParkId(carParkId);
        List<Coordinate> coordinateList = new ArrayList<>();
        coordinateList.add(getCoordinate(carParkId, baiduMap, CoordinateTypeEnum.BAI_DU_MAP.getCode()));
        coordinateList.add(getCoordinate(carParkId, gaoduMap, CoordinateTypeEnum.GAO_DE_MAP.getCode()));
        coordinateRepository.saveAll(coordinateList);
        return coordinateList;
    }

    @Override
    public CarPark findByGateway(String groupCode) {
        CarPark carPark = carParkComponent.findByGateway(groupCode);
        return carPark;
    }
    @Override
    public List<CityParkListResponse> carParkList(Authentication authentication) {
        AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
        List<Long> accountParkIds = accountDataClient.getParkDataAccount(authenticationTokenAnalysis.getUserId());
//        List<Long> accountParkIds = Arrays.asList(1026L,1031L);
        log.info(JsonUtil.toJson(accountParkIds));
//        List<Long> accountParkIds = null;//accountDataClient.getParkDataAccount(authenticationTokenAnalysis.getUserId());
        List<CityParkListResponse> citys = new ArrayList<>();
        List<CarPark> carParks = null;
        if(accountParkIds == null){
            log.info("is null find park all");
            carParks = carParkRepository.findAll();
        }else {
            log.info("not null find park by ids");
            carParks = carParkRepository.findAllById(accountParkIds);
            log.info(JsonUtil.toJson(carParks));
        }
        if(carParks == null || carParks.size() == 0) {
        	 return citys;
        }
        List<Long> parkIds = carParks.stream().map(car -> car.getId()).collect(Collectors.toList());
        List<CarPlace> carPlaces = carPlaceService.findCarPlaceByParkIds(parkIds);
        CityParkListResponse city = null;
        if (carParks == null || carParks.size() == 0) {
            return citys;
        }
        Set<String> collect = carParks.stream().map(car -> car.getCityCode()).collect(Collectors.toSet());
        CarParkListResponse carParkList = null;
        for (String code : collect) {
            city = new CityParkListResponse();
            List<CarPark> parks = carParks.stream().filter(car -> car.getCityCode().equals(code)).collect(Collectors.toList());
            int i = 0;
            for (CarPark carPark : parks) {
                if (i == 0) {
                    city.setCityCode(carPark.getCityCode());
                    city.setCityName(carPark.getCityName());
                }
                carParkList = new CarParkListResponse();
                carParkList.setId(carPark.getId());
                carParkList.setParkName(carPark.getParkName());
                carParkList = computeCarPlaceStatus(carParkList, carPlaces);
                city.getPrakList().add(carParkList);
                i++;
            }
            citys.add(city);
        }
        return citys;
    }


    private CarParkListResponse computeCarPlaceStatus(CarParkListResponse carPark,List<CarPlace> carPlaces) {
        int ownerAmount = 0, ownerUseAmount = 0, tempAmount = 0, tempUseAmount = 0, carAmount = 0, carUseAmount = 0,
                leisureAmount = 0, faultAmount = 0;
        for (CarPlace car : carPlaces) {
            if (car.getParkId().longValue() == carPark.getId().longValue()) {
                if (car.getLockStatus() == null || car.getLockStatus().intValue() == 3) {
                    faultAmount++;
                }
                switch (car.getPlaceType() != null ? car.getPlaceType() : 1) {
                    case 2:
                        carAmount++;
                        ownerAmount++;
                        if (car.getPlaceStatus().intValue() == 1) {
                            carUseAmount++;
                            ownerUseAmount++;
                        } else if (car.getPlaceStatus().intValue() == 0) {
                            leisureAmount++;
                        }
                        break;
                    case 1:
                        carAmount++;
                        tempAmount++;
                        if (car.getPlaceStatus().intValue() == 1) {
                            carUseAmount++;
                            tempUseAmount++;
                        } else if (car.getPlaceStatus().intValue() == 0) {
                            leisureAmount++;
                        }
                        break;
                }
            }
        }
        carPark.setParkAllAmount(carAmount);
        carPark.setParkLeisureAmount(leisureAmount);
        carPark.setParkFaultAmount(faultAmount);
        carPark.setParkUseNumber(carUseAmount);
        carPark.getCarPlaceType().put("固定",new CarParkListResponse.ParkCarPlaceTypeBuilder().type((short)2).typeName("固定").parkAmount(ownerAmount).parkUseAmount(ownerUseAmount).builder());
        carPark.getCarPlaceType().put("临停",new CarParkListResponse.ParkCarPlaceTypeBuilder().type((short)1).typeName("临停").parkAmount(tempAmount).parkUseAmount(tempUseAmount).builder());
        carPark.getCarPlaceType().put("VIP",new CarParkListResponse.ParkCarPlaceTypeBuilder().type((short)3).typeName("VIP").parkAmount(0).parkUseAmount(0).builder());
        return carPark;
    }
	@Override
	public List<CarParkFloorsResponse> carParkFloors(Long carParkId) {
		List<CarParkFloorsResponse> ParkFloors = new ArrayList<>();
		CarParkFloorsResponse floor = null;
		List<FloorPlan> floors = this.floorPlanRepository.getAllByParkId(carParkId);
		if(floors == null || floors.size() == 0) {
			return ParkFloors;
		}
		for (FloorPlan f : floors) {
			floor = new CarParkFloorsResponse(f.getId(),f.getFloorName());
			ParkFloors.add(floor);
		}
		return ParkFloors;
	}



}
