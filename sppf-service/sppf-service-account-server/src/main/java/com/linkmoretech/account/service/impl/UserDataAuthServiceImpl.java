package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.entity.AuthUserPark;
import com.linkmoretech.account.entity.AuthUserPlace;
import com.linkmoretech.account.entity.Resources;
import com.linkmoretech.account.entity.User;
import com.linkmoretech.account.enums.AuthTypeEnum;
import com.linkmoretech.account.resposity.AuthUserParkRepository;
import com.linkmoretech.account.resposity.AuthUserPlaceRepository;
import com.linkmoretech.account.resposity.UserRepository;
import com.linkmoretech.account.service.UserDataAuthService;
import com.linkmoretech.account.vo.request.AuthParkAddRequest;
import com.linkmoretech.account.vo.request.SearchRequest;
import com.linkmoretech.account.vo.response.AuthDataListResponse;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.sun.deploy.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public void addParkAuth(AuthParkAddRequest authParkAddRequest) {
        /**
         * 检查该车场是否已添加
         * */
        List<AuthUserPark> authUserParkList = authUserParkRepository.getAllByUserIdAndParkIdIn(authParkAddRequest.getUserId(),
                authParkAddRequest.getParkIdList());
        List<Long> authParkId = authParkAddRequest.getParkIdList();
        if (authUserParkList != null) {
            List<Long> parkIdList = authUserParkList.stream().map(AuthUserPark::getParkId).collect(Collectors.toList());
            authParkId = authParkAddRequest.getParkIdList().stream().filter(parkId -> !parkIdList.contains(parkId)).collect(Collectors.toList());
        }
        log.info("本次提交{}" , authParkId);
        List<AuthUserPark> authUserParks = authParkId.stream().map(parkId -> {
            AuthUserPark authUserPark = new AuthUserPark();
            authUserPark.setParkId(parkId);
            authUserPark.setUserId(authParkAddRequest.getUserId());
            authUserPark.setAuthType(AuthTypeEnum.getStatus(authParkAddRequest.getAuthStatus()).getCode());
            return authUserPark;
        }).collect(Collectors.toList());
        authUserParkRepository.saveAll(authUserParks);
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
                    authUserPark.getParkName(), null);

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
    public List<String> getPlaceNoList(Long userId, Long parkId) {
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
        return authUserPlaces.stream().map(AuthUserPlace::getPlaceNo).collect(Collectors.toList());
    }
}
