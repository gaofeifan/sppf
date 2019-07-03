package com.linkmoretech.user.service.impl;

import com.linkmoretech.client.LeasePlaceClient;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.common.LeaseInput;
import com.linkmoretech.parking.common.LeaseOutput;
import com.linkmoretech.user.entity.LeaseUser;
import com.linkmoretech.user.entity.LicensePlate;
import com.linkmoretech.user.repository.LeaseUserRepository;
import com.linkmoretech.user.repository.LicensePlateRepository;
import com.linkmoretech.user.repository.UserInfoRepository;
import com.linkmoretech.user.service.LeaseUserService;
import com.linkmoretech.user.vo.response.LeasePlaceInfoResponse;
import com.linkmoretech.user.vo.response.LeaseUserListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description:
 * @date: 16:40 2019-05-23
 */
@Service
@Slf4j
public class LeaseUserServiceImpl implements LeaseUserService {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    LicensePlateRepository licensePlateRepository;

    @Autowired
    LeaseUserRepository leaseUserRepository;

    @Autowired
    LeasePlaceClient leasePlaceClient;

    @Override
    public PageDataResponse<LeaseUserListResponse> searchPage(PageSearchRequest pageSearchRequest) {

        Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest
                .getPageSize());
        Page<LeaseUser> leaseUsers = leaseUserRepository.findAll(pageable);
        PageDataResponse<LeaseUserListResponse> dataResponse = new PageDataResponse<>();
        dataResponse.setTotal(leaseUsers.getTotalElements());
        List<LeaseUser> leaseUserList = leaseUsers.getContent();
        /**
         * 根据当前用户UserId 查询该用户添加的车牌号
         * 拼装发起调用的参数
         * leaseCode 用户的车牌号
         * */
        List<Long> userIds = leaseUserList.stream().map(LeaseUser::getUserId).collect(Collectors.toList());
        /**
         * 查询用户添加的车牌号
         * */
        List<LicensePlate> licensePlateList = licensePlateRepository.findAllByUserIdIn(userIds);
        /**
         * 车牌号 对应 的用户信息
         * */
        Map<Long, List<String>> licenseMap = licensePlateList.stream().collect(Collectors.groupingBy(LicensePlate::getUserId,
                Collectors.mapping(LicensePlate::getPlateNo,Collectors.toList())));

        List<LeaseUserListResponse> leaseUserListResponseList = new ArrayList<>();
        leaseUserList.forEach(leaseUser -> {
            LeaseUserListResponse leaseUserListResponse = new LeaseUserListResponse();
            leaseUserListResponse.setUserId(leaseUser.getUserId());
            leaseUserListResponse.setLinkMobile(leaseUser.getMobile());
            /**
             * 得到其用户车牌号 和 该用户长租授权码
             * */
            List<String> plateList = licenseMap.get(leaseUser.getUserId());
            /**
             * 过滤车牌号
             * */
            LeaseInput leaseInput  = new LeaseInput(leaseUser.getLeaseCode(), plateList);

            LeaseOutput leaseOutput = leasePlaceClient.getLeasePlaceList(leaseInput);

            if (leaseOutput != null) {
                List<LeasePlaceInfoResponse> placeInfoResponses = leaseOutput.getPlaceNoList().stream()
                        .map(leasePlaceOutput -> new LeasePlaceInfoResponse(leasePlaceOutput.getPlaceNo(),
                                leasePlaceOutput.getLicensePlateNo())).collect(Collectors.toList());
                /**
                 * 调用车区服务获取该用户车牌号下 该授权码下的可用车位及车牌号
                 * */
                leaseUserListResponse.setParkName(leaseOutput.getParkName());
                leaseUserListResponse.setStartDate(leaseOutput.getStartDate());
                leaseUserListResponse.setEndDate(leaseOutput.getEndDate());
                leaseUserListResponse.setStatus(leaseOutput.getLeaseStatus());
                leaseUserListResponse.setLeasePlaceInfoResponses(placeInfoResponses);
            }
            leaseUserListResponseList.add(leaseUserListResponse);
        });
        dataResponse.setData(leaseUserListResponseList);
        return dataResponse;
    }
}
