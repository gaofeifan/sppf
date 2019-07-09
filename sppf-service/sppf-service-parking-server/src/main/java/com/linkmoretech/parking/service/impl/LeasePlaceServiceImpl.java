package com.linkmoretech.parking.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.common.*;
import com.linkmoretech.parking.component.CarParkComponent;
import com.linkmoretech.parking.component.LeaseOrderComponent;
import com.linkmoretech.parking.entity.CarPark;
import com.linkmoretech.parking.entity.CarPlace;
import com.linkmoretech.parking.entity.LeasePlace;
import com.linkmoretech.parking.entity.LicensePlate;
import com.linkmoretech.parking.enums.CarPlaceTypeEnum;
import com.linkmoretech.parking.enums.LineStatusEnum;
import com.linkmoretech.parking.enums.UserStatusEnum;
import com.linkmoretech.parking.repository.CarParkRepository;
import com.linkmoretech.parking.repository.CarPlaceRepository;
import com.linkmoretech.parking.repository.LeasePlaceRepository;
import com.linkmoretech.parking.repository.LicensePlateRepository;
import com.linkmoretech.parking.service.LeasePlaceService;
import com.linkmoretech.parking.vo.request.LeasePlaceBatchRequest;
import com.linkmoretech.parking.vo.request.LeasePlaceCreateRequest;
import com.linkmoretech.parking.vo.response.LeasePlaceEditResponse;
import com.linkmoretech.parking.vo.response.LeasePlaceInfoResponse;
import com.linkmoretech.parking.vo.response.LeasePlaceListResponse;
import com.linkmoretech.parking.vo.response.LeasePlaceResponse;
import com.linkmoretech.versatile.client.AreaCityClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @Author: alec
 * Description: 长租车位业务逻辑层
 * @date: 17:19 2019-05-08
 */
@Service
@Slf4j
public class LeasePlaceServiceImpl implements LeasePlaceService {

    @Autowired
    LeasePlaceRepository leasePlaceRepository;
    @Autowired
    LicensePlateRepository licensePlateRepository;
    @Autowired
    CarParkRepository carParkRepository;
    @Autowired
    CarPlaceRepository carPlaceRepository;
    @Autowired
    CarParkComponent carParkComponent;
    @Autowired
    LeaseOrderComponent leaseOrderComponent;
    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    AreaCityClient areaCityClient;


    @Override
    public PageDataResponse<LeasePlaceListResponse> searchPage(PageSearchRequest pageSearchRequest) {
        Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest
                .getPageSize(), Sort.by(Sort.Order.asc("endDate"))
                .and(Sort.by(Sort.Order.desc("parkId")))
                .and(Sort.by(Sort.Order.desc("leaseCode"))));
        Page page = leasePlaceRepository.findAll(pageable);
        PageDataResponse<LeasePlaceListResponse> pageDataResponse = new PageDataResponse<>();
        pageDataResponse.setTotal(page.getTotalElements());
        List<LeasePlace> leasePlaceList = page.getContent();
        List<Long> placeIds = leasePlaceList.stream().map(LeasePlace::getPlaceId).collect(Collectors.toList());
        log.info("placeIds {}" , placeIds);
        List<LicensePlate> licensePlates = licensePlateRepository.getAllByPlaceIdIn(placeIds);
        log.info("licensePlates {}", licensePlates);
        Map<Long, List<LicensePlate>> licensePlateMap = licensePlates.stream().collect(Collectors.groupingBy(
                LicensePlate::getPlaceId));
        log.info("group : {}", licensePlateMap);
        List<LeasePlaceListResponse> leaseListResponsePlaceList = leasePlaceList.stream().map(leasePlace -> {
            LeasePlaceListResponse leasePlaceListResponse = new LeasePlaceListResponse();
            BeanUtils.copyProperties(leasePlace, leasePlaceListResponse);
            if (licensePlateMap.get(leasePlace.getPlaceId()) != null) {
                List<String> plateList = licensePlateMap.get(leasePlace.getPlaceId()).stream()
                        .map(LicensePlate::getLicensePlateNo).collect(Collectors.toList());
                leasePlaceListResponse.setLicensePlateNoList(plateList);
            }
            return leasePlaceListResponse;
        }).collect(Collectors.toList());
        pageDataResponse.setData(leaseListResponsePlaceList);
        return pageDataResponse;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(LeasePlaceCreateRequest leasePlaceCreateRequest) throws CommonException {
        /**
         * 1 当前车位是否已是长租车位
         * 2 创建长租车位记录
         * 3 创建车位与车牌对应关系(默认不限制使用 n * n 方式记录)
         * 4 更新对应车场类型(如果全部车位是固定 更新为固定 否则更新为组合)
         * 5 更新对应车位类型(将车位类型更新为 固定)
         * */
        CarPark carPark = carParkComponent.getCarPark(leasePlaceCreateRequest.getParkId());
        List<CarPlace> carPlaceList = carPlaceRepository.getAllByParkIdAndIdIn(leasePlaceCreateRequest.getParkId(),
                leasePlaceCreateRequest.getPlaceIdList());
        validatePlace(carPlaceList);
        Date currentDate = new Date();
        String leaseCode = leaseOrderComponent.getLeaseCode(carPark);
        List<LeasePlace> leasePlaceList =carPlaceList.stream().map(carPlace -> {
            LeasePlace leasePlace = new LeasePlace();
            BeanUtils.copyProperties(leasePlaceCreateRequest, leasePlace);
            leasePlace.setParkId(carPlace.getParkId());
            leasePlace.setParkName(carPlace.getParkName());
            leasePlace.setPlaceId(carPlace.getId());
            leasePlace.setPlaceNo(carPlace.getPlaceNo());
            leasePlace.setLeaseStatus(UserStatusEnum.ENABLED.getCode());
            leasePlace.setCreateTime(currentDate);
            leasePlace.setUpdateTime(currentDate);
            leasePlace.setLeaseCode(leaseCode);
            return leasePlace;
        }).collect(Collectors.toList());
       log.info("存储长租车位信息 {}", leasePlaceList);
       List<LeasePlace> resultData = leasePlaceRepository.saveAll(leasePlaceList);
       List<LicensePlate> licensePlateList =  batchSaveLicensePlate(resultData, leasePlaceCreateRequest.getLicensePlateList());
       batchUpdateStatus(carPark, resultData, leasePlaceCreateRequest.getUsername(), CarPlaceTypeEnum.FIXED_PLACE);
       decreaseStock(licensePlateList);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchImport(LeasePlaceBatchRequest leasePlaceBatchRequest) throws CommonException {
        /**
         * 验证车场信息
         * */
        CarPark carPark = carParkComponent.getCarPark(leasePlaceBatchRequest.getParkId());
        /**
         * 解析Excel 拆分成参数列表
         * */
        List<String> leasePlaceList = new ArrayList<>();
        Map<String, List<String>> licensePlateMap = new HashMap<>();
        Map<String, LeasePlace> leasePlaceMap = new HashMap<>();
        Date currentDate = new Date();
        leasePlaceBatchRequest.getLeasePlaceBatchParamsRequests().forEach(params -> {
            log.info("place list {}", params.getPlaceList());
            leasePlaceList.addAll(params.getPlaceList());
            params.getPlaceList().forEach(place -> {
                licensePlateMap.put(place, params.getLicensePlateList());
                LeasePlace leasePlace = new LeasePlace();
                leasePlace.setPlaceNo(place);
                leasePlace.setParkName(carPark.getParkName());
                leasePlace.setParkId(carPark.getId());
                leasePlace.setLinkMobile(params.getLinkMobile());
                leasePlace.setStartDate(params.getStartDate());
                leasePlace.setEndDate(params.getEndDate());
                leasePlace.setCreateTime(currentDate);
                leasePlace.setUpdateTime(currentDate);
                leasePlace.setLeaseStatus(UserStatusEnum.ENABLED.getCode());
                leasePlaceMap.put(place, leasePlace);
            });
        });
        /**
         * 检测车位编号是否合法
         * */
        log.info("car id {} and place {}", carPark.getId(), leasePlaceList);
        List<CarPlace> carPlaceList = carPlaceRepository.getAllByParkIdAndPlaceNoIn(carPark.getId(), leasePlaceList);
        validatePlace(carPlaceList);
        for (CarPlace carPlace : carPlaceList) {
            leasePlaceMap.get(carPlace.getPlaceNo()).setPlaceId(carPlace.getId());
        }
        List<LeasePlace> resultData = leasePlaceRepository.saveAll(leasePlaceMap.values());
        List<LicensePlate> licensePlateList = batchSaveLicensePlate(licensePlateMap, leasePlaceMap);
        decreaseStock(licensePlateList);
        /**
         * 更新存储车牌信息
         * */
        batchUpdateStatus(carPark, resultData, leasePlaceBatchRequest.getUsername(), CarPlaceTypeEnum.FIXED_PLACE);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeId(Long leaseId, String username) throws CommonException {
        LeasePlace leasePlace = leasePlaceRepository.findById(leaseId).get();
        if (leasePlace.getId() == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "未发现有效记录");
        }
        Optional<CarPark> optional = carParkRepository.findById(leasePlace.getParkId());
        leasePlaceRepository.deleteById(leaseId);
        if (optional.isPresent()) {
            CarPark carPark = optional.get();
            Long placeId = leasePlace.getPlaceId();
            List<Long> placeIdList = new ArrayList<>();
            placeIdList.add(placeId);
            /**
             * 将该车位变更为临停车位
             * */
            batchUpdateStatus(carPark,placeIdList, new Date(), username, CarPlaceTypeEnum.TEMP_PLACE);
            /**
             * 删除该长租车位对应车牌号
             * */
            licensePlateRepository.deleteAllByParkIdAndPlaceId(leasePlace.getParkId(), leasePlace.getPlaceId());
        }
    }
    @Override
    public LeaseOutput getLeasePlaceList(LeaseInput leaseInput) {
        log.info("车区服务提供 查询个人授权详情{}", leaseInput);
        /**
         * 根据传入车牌号 查询该车牌号所有对应对 长租信息
         *
         * leaseCode 车场名称 车场ID
         * 车位名称 车位编码 status
         * 车位名称 车位编码 status
         * */
        LeasePlace leasePlace = leasePlaceRepository.getFirstByLeaseCode(leaseInput.getLeaseCode());
        if (leasePlace == null) {
            return null;
        }
        List<LicensePlate> licensePlateList = licensePlateRepository
                .getAllByLicensePlateNoInAndLeaseCode(leaseInput.getPlateCodeList(), leaseInput.getLeaseCode());

        LeaseOutput leaseOutput = new LeaseOutput();
        BeanUtils.copyProperties(leasePlace, leaseOutput);
        List<LeasePlaceOutput> leasePlaceOutputs = licensePlateList.stream().map(licensePlate -> {
            LeasePlaceOutput leasePlaceOutput = new LeasePlaceOutput();
            BeanUtils.copyProperties(licensePlate, leasePlaceOutput);
            return leasePlaceOutput;
        }).collect(Collectors.toList());
        /**
         * 设定该授权下的车位
         * */
        leaseOutput.setPlaceNoList(leasePlaceOutputs);
        return leaseOutput;
    }
    @Override
    public void updateEnableStatus(Long leaseId, Integer status, String username) throws CommonException {
        LeasePlace leasePlace = leasePlaceRepository.findById(leaseId).get();
        if (leasePlace.getId() == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "未发现有效记录");
        }
        UserStatusEnum userStatusEnum = UserStatusEnum.getStatus(status);
        if (userStatusEnum == null ){
            throw new CommonException(ResponseCodeEnum.ERROR, "状态码不正确");
        }
        /**
         * 修改当前记录状态
         * */
        Optional<CarPark> optional = carParkRepository.findById(leasePlace.getParkId());
        /**
         * 更新长租车位状态
         * */
        leasePlace.setLeaseStatus(UserStatusEnum.getStatus(status).getCode());
        leasePlace.setUpdateTime(new Date());
        leasePlace.setUpdateBy(username);
        leasePlaceRepository.save(leasePlace);

        if (optional.isPresent()) {
            CarPark carPark = optional.get();
            Long placeId = leasePlace.getPlaceId();
            List<Long> placeIdList = new ArrayList<>();
            placeIdList.add(placeId);
            /**
             * 将该车位变更为临停车位
             * */
            CarPlaceTypeEnum carPlaceType = CarPlaceTypeEnum.TEMP_PLACE;
            if (status.equals(UserStatusEnum.ENABLED.getCode())) {
                carPlaceType = CarPlaceTypeEnum.FIXED_PLACE;
            }
            batchUpdateStatus(carPark,placeIdList, new Date(), username, carPlaceType);
            /**
             * 更新长租车位状态长租车位对应车牌号
             * */
            licensePlateRepository.updateLicensePlateStatus(carPark.getId(), placeId, status);
        }
    }

    @Override
    public LeasePlaceInfoResponse getLeasePlaceDetail(Long id) throws CommonException {
        LeasePlaceInfoResponse leasePlaceInfoResponse = new LeasePlaceInfoResponse();
        Optional<LeasePlace> optional = leasePlaceRepository.findById(id);
        if (!optional.isPresent()) {
            return leasePlaceInfoResponse;
        }
        LeasePlace leasePlace = optional.get();
        BeanUtils.copyProperties(leasePlace, leasePlaceInfoResponse);

        CarPark carPark = carParkComponent.getCarPark(leasePlace.getParkId());
        log.info("车场 {}", carPark);
        leasePlaceInfoResponse.setCityName(carPark.getCityName());
        List<LicensePlate> licensePlateList = licensePlateRepository.getAllByPlaceNoAndLeaseCode(leasePlace.getPlaceNo(),
                leasePlace.getLeaseCode());
        if (licensePlateList != null) {
            List<String> licensePlateNo = licensePlateList.stream().map(LicensePlate::getLicensePlateNo).collect(Collectors.toList());
            leasePlaceInfoResponse.setLicensePlateNoList(licensePlateNo);
        }
        log.info("查询info {}", leasePlaceInfoResponse);
        return leasePlaceInfoResponse;

    }

    @Override
    public LeasePlaceEditResponse getDetail(String leaseCode) throws CommonException {
        List<LeasePlace> leasePlaceList = leasePlaceRepository.getAllByLeaseCode(leaseCode);
        LeasePlaceEditResponse leasePlaceEditResponse = new LeasePlaceEditResponse();
        if (leasePlaceList == null) {
            return  leasePlaceEditResponse;
        }
        LeasePlace firstLeasePlace = leasePlaceList.get(0);
        BeanUtils.copyProperties(firstLeasePlace, leasePlaceEditResponse);
        CarPark carPark = carParkComponent.getCarPark(firstLeasePlace.getParkId());
        List<String> cityCodes = areaCityClient.getAllCityCode(carPark.getCityCode());
        leasePlaceEditResponse.setCityCodes(cityCodes);
        List<LeasePlaceResponse> placeIdList = leasePlaceList.stream()
                .map(leasePlace -> {
                    LeasePlaceResponse leasePlaceResponse = new LeasePlaceResponse();
                    leasePlaceResponse.setId(leasePlace.getPlaceId());
                    leasePlaceResponse.setPlaceNo(leasePlace.getPlaceNo());
                    return leasePlaceResponse;
                }).collect(Collectors.toList());
        leasePlaceEditResponse.setPlaceIdList(placeIdList);
        List<LicensePlate> licensePlateList = licensePlateRepository.getAllByLeaseCode(leaseCode);
        List<String> licensePlateNoList = licensePlateList.stream().map(LicensePlate::getLicensePlateNo).collect(Collectors.toList());
        leasePlaceEditResponse.setLicensePlateList(licensePlateNoList);
        return leasePlaceEditResponse;

    }

    private List<LicensePlate> batchSaveLicensePlate(Map<String, List<String>> licensePlateMap,
                                       Map<String, LeasePlace> placeMap) {
        List<LicensePlate> licensePlates = new ArrayList<>();
        Iterator<String> iterator = licensePlateMap.keySet().iterator();
        while (iterator.hasNext()) {
            String placeNo = iterator.next();
            List<String> plateList = licensePlateMap.get(placeNo);
            licensePlates.addAll(getLicensePlateList(plateList, placeMap.get(placeNo)));
        }
        List<LicensePlate> list = licensePlateRepository.saveAll(licensePlates);
        return list;
    }
    private List<LicensePlate> batchSaveLicensePlate(List<LeasePlace> resultData, List<String> licensePlateList) {
        List<LicensePlate> licensePlates = new ArrayList<>();
        resultData.forEach((LeasePlace leasePlace) -> licensePlates.addAll(getLicensePlateList(licensePlateList, leasePlace)));
        List<LicensePlate> list = licensePlateRepository.saveAll(licensePlates);
        return list;
    }
    private List<LicensePlate> getLicensePlateList(List<String> licensePlateList, LeasePlace leasePlace) {
        List<LicensePlate> licensePlates = new ArrayList<>();
        licensePlateList.forEach(licensePlateNo -> {
            LicensePlate license = new LicensePlate();
            license.setLeaseCode(leasePlace.getLeaseCode());
            license.setParkId(leasePlace.getParkId());
            license.setPlaceId(leasePlace.getPlaceId());
            license.setPlaceNo(leasePlace.getPlaceNo());
            license.setLicensePlateNo(licensePlateNo);
            license.setPlateStatus(UserStatusEnum.ENABLED.getCode());
            licensePlates.add(license);
        });
        return licensePlates;
    }
    private void validatePlace(List<CarPlace> carPlaceList) throws CommonException {
        if (carPlaceList.size() == 0) {
            throw new CommonException(ResponseCodeEnum.ERROR, "未发现有效车位");
        }
        List<CarPlace> disableList = carPlaceList.stream().filter(carPlace ->
                carPlace.getLineStatus().equals(LineStatusEnum.OFFLINE.getCode()) ||
                        carPlace.getPlaceType().equals(CarPlaceTypeEnum.FIXED_PLACE.getCode())
        ).collect(Collectors.toList());
        if (disableList.size() > 0) {
            disableList.forEach(carPlace -> {
                log.warn("车位 {}, 类型 {} , 状态 {} , 不可用", carPlace.getPlaceNo(), carPlace.getPlaceType(), carPlace.getLineStatus());
            });
            throw new CommonException(ResponseCodeEnum.ERROR, disableList.size() + "个车位处于下线状态或已是长租车位");
        }
    }
    private void batchUpdateStatus(CarPark carPark, List<LeasePlace> resultData, String updateBy, CarPlaceTypeEnum carPlaceTypeEnum) {
        List<Long> placeIdList = resultData.stream().map(LeasePlace::getPlaceId).collect(Collectors.toList());
        batchUpdateStatus(carPark, placeIdList, new Date(), updateBy, carPlaceTypeEnum);
    }
    private void batchUpdateStatus(CarPark carPark, List<Long> placeIdList, Date currentDate, String updateBy, CarPlaceTypeEnum carPlaceTypeEnum) {
        /**
         * 编辑车位位固定车位
         * */
        log.info("批量更新车位属性 {} - {}", placeIdList, carPlaceTypeEnum.getMessage());
        carPlaceRepository.updatePlaceType(placeIdList, carPlaceTypeEnum.getCode(), currentDate, updateBy);
        carParkComponent.updateCarParkType(carPark);
    }
    private void decreaseStock(List<LicensePlate> licensePlates) {
        List<LicensePlateOutput> licensePlateOutputs = new ArrayList<>();
        Map<String, List<LicensePlate>> listMap = licensePlates.stream().collect(Collectors.groupingBy(LicensePlate::getLeaseCode));
        for(Map.Entry<String, List<LicensePlate>>  leaseMap: listMap.entrySet()) {
            LicensePlateOutput licensePlateOutput = new LicensePlateOutput();
            licensePlateOutput.setLeaseCode(leaseMap.getKey());
            List<String> licensePlateList = leaseMap.getValue().stream().map(LicensePlate::getLicensePlateNo).collect(Collectors.toList());
            licensePlateOutput.setLicensePlateNoList(licensePlateList);
            licensePlateOutputs.add(licensePlateOutput);
        }
        /**
         * 通过mq异步发送长租车位信息
         * */
        if (licensePlateOutputs.size() > 0 ){
            amqpTemplate.convertAndSend(MqLeasePlaceQueueCommon.ADD_LEASE_PLACE_QUEUE, JSONObject.toJSONString(licensePlateOutputs));
        }
    }
}
