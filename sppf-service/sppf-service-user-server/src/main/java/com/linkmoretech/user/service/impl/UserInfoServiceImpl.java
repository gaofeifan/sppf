package com.linkmoretech.user.service.impl;

import com.linkmoretech.common.config.SnowflakeIdGenerator;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.user.common.vo.UserInfoInput;
import com.linkmoretech.user.entity.LicensePlate;
import com.linkmoretech.user.entity.UserInfo;
import com.linkmoretech.user.enums.UserSourceEnum;
import com.linkmoretech.user.enums.UserStatusEnum;
import com.linkmoretech.user.enums.UserTypeEnum;
import com.linkmoretech.user.repository.LicensePlateRepository;
import com.linkmoretech.user.repository.UserInfoRepository;
import com.linkmoretech.user.service.UserInfoService;
import com.linkmoretech.user.vo.UserEditRequest;
import com.linkmoretech.user.vo.UserInfoResponse;
import com.linkmoretech.user.vo.UserListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * @Description:
 * @date: 上午11:31 2019/4/11
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    LicensePlateRepository licensePlateRepository;

    @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public UserInfoResponse findDetailByUserId(String userId) {
        UserInfo userInfo = userInfoRepository.getOne(userId);
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        BeanUtils.copyProperties(userInfo, userInfoResponse);
        return userInfoResponse;
    }

    @Override
    public PageDataResponse<UserListResponse> searchList(PageSearchRequest pageSearchRequest) {
        Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest
        .getPageSize());
        /**
         * 省略查询条件
         * */
        Page<UserInfo> userInfoPage = userInfoRepository.findAll(pageable);
        PageDataResponse<UserListResponse> dataResponse = new PageDataResponse<>();
        dataResponse.setTotal(userInfoPage.getTotalElements());
        List<UserListResponse> userListResponseList = new ArrayList<>();
        List<UserInfo> userInfoList = userInfoPage.getContent();
        log.info("user data list length {}", userInfoList.size());
        List<String> userId = userInfoList.stream().map(UserInfo::getId).collect(Collectors.toList());
        List<LicensePlate> licensePlates = licensePlateRepository.findAllByUserIdIn(userId);
        Map<String, List<LicensePlate>> licensePlateMap = licensePlates.stream().collect(Collectors.groupingBy(LicensePlate:: getUserId));
        userInfoList.forEach(userInfo -> {
            UserListResponse userListResponse = new UserListResponse();
            BeanUtils.copyProperties(userInfo, userListResponse);
            List<LicensePlate> userLicensePlate = licensePlateMap.get(userInfo.getId());
            if (userLicensePlate != null) {
                userListResponse.setPlatNumbers(userLicensePlate.stream().map(LicensePlate :: getPlateNo).collect(Collectors.toList()));
            }
            userListResponseList.add(userListResponse);
        });
        dataResponse.setData(userListResponseList);
        return dataResponse;
    }

    @Override
    public void editUser(UserEditRequest userEditRequest) throws CommonException {
        UserInfo userInfo = userInfoRepository.getOne(userEditRequest.getId());
        if (userInfo == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "用户数据未找到");
        }
        if (!StringUtils.isEmpty(userEditRequest.getUserNick())) {
            userInfo.setUserNick(userEditRequest.getUserNick());
        }
        if (!StringUtils.isEmpty(userEditRequest.getUserSex())) {
            userInfo.setUserSex(userEditRequest.getUserSex());
        }
        if (!StringUtils.isEmpty(userEditRequest.getVehicleBrand())) {
            userInfo.setVehicleBrand(userEditRequest.getVehicleBrand());
        }
        if (!StringUtils.isEmpty(userEditRequest.getVehicleType())) {
            userInfo.setVehicleType(userEditRequest.getVehicleType());
        }
        UserInfo returnUser = userInfoRepository.saveAndFlush(userInfo);
        log.info("update userInfo success  updateTime {}", returnUser.getUpdateTime());
    }

    @Override
    public void updateUserState(String userId, String username, Integer userState) throws CommonException {
        UserInfo userInfo = userInfoRepository.getOne(userId);
        if (userInfo == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "用户数据未找到");
        }
        userInfo.setUserStatus(userState);
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateBy(username);
        UserInfo returnUser = userInfoRepository.saveAndFlush(userInfo);
        log.info("update userStatus success  updateTime {}", returnUser.getUpdateTime());
    }

    @Override
    public void updateUserMobile(UserInfoInput userInfoInput) throws CommonException {
        /**
         * 修改手机号的
         * 判断当前用户的是否存在 如果不存在抛出异常
         * 当前用户的手机号是否已存在 如果已存在抛出异常
         * 修改当前用户手机号
         * */
        if (StringUtils.isEmpty(userInfoInput.getUserMobile())) {
            throw new CommonException(ResponseCodeEnum.ERROR, "用户数据未找到");
        }
        UserInfo userInfo = userInfoRepository.getOne(userInfoInput.getUserId());
        if (userInfo == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "用户数据未找到");
        }
        userInfo = userInfoRepository.findUserInfoByUserMobile(userInfoInput.getUserMobile());
        if (userInfo != null && userInfo.getId().equals(userInfoInput.getUserId())) {
            log.warn("user id {} personal , system user {} personal equal and value {}", userInfo.getId(),
                    userInfoInput.getUserId(), userInfoInput.getUserMobile());
            throw new CommonException(ResponseCodeEnum.ERROR, "手机号已存在");
        }
        userInfo.setUserMobile(userInfoInput.getUserMobile());
        UserInfo returnUser = userInfoRepository.saveAndFlush(userInfo);
        log.info("update userMobile success  updateTime {}", returnUser.getUpdateTime());
    }

    @Override
    public void bindWeChat(UserInfoInput userInfoInput) throws CommonException {
        UserInfo userInfo = userInfoRepository.getOne(userInfoInput.getUserId());
        if (userInfo == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "用户数据未找到");
        }
        userInfo.setWeChatBindState(1);
        userInfo.setOpenId(userInfoInput.getOpenId());
        userInfo.setWeChatBindTime(new Date());
        UserInfo returnUser = userInfoRepository.save(userInfo);
        log.info("bind weChat success openId {}, updateTime {}", returnUser.getOpenId(),returnUser.getUpdateTime());
    }

    @Override
    public void unbindWeChat(String userId) throws CommonException {
        UserInfo userInfo = userInfoRepository.getOne(userId);
        if (userInfo == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "用户数据未找到");
        }
        if (userInfo.getWeChatBindState().equals(0)) {
            throw new CommonException(ResponseCodeEnum.ERROR, "用户未绑定微信号");
        }
        userInfo.setWeChatBindState(0);
        userInfo.setOpenId(null);
        userInfo.setWeChatBindTime(null);
        UserInfo returnUser = userInfoRepository.save(userInfo);
        log.info("unbind weChat success openId {}, updateTime {}", returnUser.getOpenId(),returnUser.getUpdateTime());
    }

    @Override
    @Transactional
    public String createUser(UserInfoInput userInfoInput) throws CommonException {
        /**
         * 根据手机号查询该手机号是否已有用户
         * */
        UserInfo userInfo = userInfoRepository.findUserInfoByUserMobile(userInfoInput.getUserMobile());
        if (userInfo != null) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "手机号已存在");
        }
        userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoInput, userInfo);
        if (userInfo.getUserSource() == null) {
            userInfo.setUserSource(UserSourceEnum.APP.getCode());
        }
        String id = String.valueOf(snowflakeIdGenerator.nextId());

        userInfo.setUserType(UserTypeEnum.TEMPORARY_USER.getCode());
        userInfo.setUserStatus(UserStatusEnum.NORMAL.getCode());
        userInfo.setId(id);
        userInfo.setRegisterTime(new Date());
        UserInfo returnUser = userInfoRepository.save(userInfo);
        log.info("create user success for userId {} ", returnUser.getId());
        return returnUser.getId();
    }
}
