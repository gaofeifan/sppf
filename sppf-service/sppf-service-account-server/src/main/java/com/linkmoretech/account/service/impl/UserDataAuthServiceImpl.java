package com.linkmoretech.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.account.entity.AuthUserPark;
import com.linkmoretech.account.entity.AuthUserPlace;
import com.linkmoretech.account.entity.User;
import com.linkmoretech.account.enums.AuthTypeEnum;
import com.linkmoretech.account.resposity.AuthUserParkRepository;
import com.linkmoretech.account.resposity.AuthUserPlaceRepository;
import com.linkmoretech.account.resposity.UserRepository;
import com.linkmoretech.account.service.UserDataAuthService;
import com.linkmoretech.account.vo.request.AuthParkAddRequest;
import com.linkmoretech.account.vo.request.AuthPlaceAddRequest;
import com.linkmoretech.account.vo.response.AuthDataListResponse;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.util.CharUtil;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description:
 * @date: 16:39 2019-06-24
 */
@Service
@Slf4j
public class UserDataAuthServiceImpl implements UserDataAuthService {

    @Autowired
    AuthUserParkRepository authUserParkRepository;

    @Autowired
    AuthUserPlaceRepository authUserPlaceRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * 添加车场数据权限
     * */
    @Override
    public void addParkAuth(AuthParkAddRequest authParkAddRequest) throws CommonException {
        /**
         * 检查该车场是否已添加
         * */

        AuthUserPark authUserPark = authUserParkRepository.getByUserIdAndParkId(authParkAddRequest.getUserId(),
                authParkAddRequest.getParkId());

        if (authUserPark != null) {
           throw new CommonException(ResponseCodeEnum.ERROR, "该车场已配置");
        }
        authUserPark = new AuthUserPark();
        authUserPark.setParkId(authParkAddRequest.getParkId());
        authUserPark.setUserId(authParkAddRequest.getUserId());
        authUserPark.setAuthType(AuthTypeEnum.getStatus(authParkAddRequest.getAuthStatus()).getCode());
        /**
         * 调用车场服务获取车场名称
         * */

        authUserParkRepository.save(authUserPark);
    }

    @Override
    public void addPlaceAuth(AuthPlaceAddRequest authPlaceAddRequest) throws CommonException {

        /**
         *  查找车场授权数据得到车场ID
         *  解析车位范围-- 格式化车位 - 转化成英文 大写
         *  调用车区服务获得车位ID，车位编号
         *  存储记录
         * */
        Optional<AuthUserPark> optional = authUserParkRepository.findById(authPlaceAddRequest.getAuthParkId());

        if (!optional.isPresent()) {
            throw new CommonException(ResponseCodeEnum.ERROR, "数据不存在");
        }

        AuthUserPark authUserPark =  optional.get();

        List<String[]> placeList = authPlaceAddRequest.getPlaceNo();

        List<String[]> formatPlace = placeList.stream().map(places -> {
            String[] place = new String[places.length];
            place[0] = CharUtil.change(places[0]).toUpperCase();
            place[1] = CharUtil.change(places[1]).toUpperCase();
            return place;
        }).collect(Collectors.toList());

        authUserPark.setPlaceDiffer(JSONObject.toJSONString(formatPlace));
        authUserParkRepository.save(authUserPark);
        /**
         * 调用车场服务 返回车位ID后存储
         * */

    }

    @Override
    public PageDataResponse<AuthDataListResponse> list(PageSearchRequest<Long> pageSearchRequest) {

        Pageable pageable  = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest.getPageSize());

        AuthUserPark searchAuth = new AuthUserPark();
        searchAuth.setUserId(pageSearchRequest.getCondition());

        Example<AuthUserPark> example = Example.of(searchAuth);

        Page<AuthUserPark> authUserParkPage = authUserParkRepository.findAll(example, pageable);

        PageDataResponse pageDataResponse = new PageDataResponse();
        pageDataResponse.setTotal(authUserParkPage.getTotalElements());

        List<AuthUserPark> authUserParks = authUserParkPage.getContent();

        List<AuthDataListResponse> authDataListResponses = new ArrayList<>();

        List<Long> placeIdList = authUserParks.stream().map(AuthUserPark::getId).collect(Collectors.toList());

        List<AuthUserPlace> authUserPlaceList = authUserPlaceRepository.getAllByAuthDataIdIn(placeIdList);

        Map<Long, List<AuthUserPlace>> authUserPlaceMap = authUserPlaceList.stream().collect(Collectors.groupingBy(AuthUserPlace :: getAuthDataId));

        authUserParks.forEach(authUserPark -> {
            AuthDataListResponse authDataListResponse = new AuthDataListResponse(authUserPark.getId(), authUserPark.getParkId(),
                    authUserPark.getParkName(), authUserPark.getAuthType(), authUserPark.getPlaceDiffer(),null);

            if (authUserPark.getAuthType().equals(AuthTypeEnum.NO_ALL.getCode()) && authUserPlaceMap != null) {
               List<AuthUserPlace> places = authUserPlaceMap.get(authUserPark.getId());
               if (places != null) {
                   authDataListResponse.setPlaceNo(places.stream().map(AuthUserPlace :: getPlaceNo).collect(Collectors.toList()));
               }
            }
            authDataListResponses.add(authDataListResponse);
        });

        pageDataResponse.setData(authDataListResponses);
        return pageDataResponse;
    }

    @Override
    public void removeParkAuth(Long parkAuthId) throws CommonException {

        Optional<AuthUserPark> optional = authUserParkRepository.findById(parkAuthId);

        if (!optional.isPresent()) {
            throw new CommonException(ResponseCodeEnum.ERROR, "数据不存在");
        }
        /**
         * 删除车位数据
         * */
        authUserPlaceRepository.deleteAllByAuthDataId(parkAuthId);
        authUserParkRepository.deleteById(parkAuthId);

    }

    @Override
    public void cleanPlaceAuth(Long parkAuthId) throws CommonException {
        Optional<AuthUserPark> optional = authUserParkRepository.findById(parkAuthId);
        if (!optional.isPresent()) {
            throw new CommonException(ResponseCodeEnum.ERROR, "数据不存在");
        }
        AuthUserPark authUserPark = optional.get();
        authUserPark.setPlaceDiffer(null);
        authUserParkRepository.save(authUserPark);
        authUserPlaceRepository.deleteAllByAuthDataId(parkAuthId);
    }

    @Override
    public List<Long> getParkIdList(Long userId) {
        log.info("userId {}", userId);
        User user  = userRepository.findById(userId).get();
        log.info("user {}", user);
        if (user.getAuthStatus().equals(AuthTypeEnum.NO_ALL.getCode())) {
            return null;
        }
        List<AuthUserPark> userParkList = authUserParkRepository.getAllByUserId(userId);
        if (userParkList == null) {
            return new ArrayList<>();
        }
        return userParkList.stream().map(AuthUserPark::getParkId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getPlaceNoList(Long userId, Long parkId) {
        User user  = userRepository.findById(userId).get();
        if (user.getAuthStatus().equals(AuthTypeEnum.NO_ALL.getCode())) {
            return null;
        }
        AuthUserPark authUserPark = authUserParkRepository.getByUserIdAndParkId(userId, parkId);
        if (authUserPark == null) {
            log.info("车位操作权限位空 == 无数据权限");
            return new ArrayList<>();
        }
        if (authUserPark.getAuthType().equals(AuthTypeEnum.NO_ALL.getCode())) {
            return null;
        }
        List<AuthUserPlace> authUserPlaces = authUserPlaceRepository.getAllByAuthDataId(authUserPark.getId());
        if (authUserPlaces == null) {
            log.info("未配置车位，既查询车场全部数据");
            return new ArrayList<>();
        }
        return authUserPlaces.stream().map(AuthUserPlace::getPlaceId).collect(Collectors.toList());
    }
}
