package com.linkmoretech.user.service.impl;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.user.component.LeaseUserComponent;
import com.linkmoretech.user.entity.LeaseUser;
import com.linkmoretech.user.entity.LicensePlate;
import com.linkmoretech.user.entity.UserInfo;
import com.linkmoretech.user.enums.LicensePlateTypeEnum;
import com.linkmoretech.user.repository.LeaseUserRepository;
import com.linkmoretech.user.repository.LicensePlateRepository;
import com.linkmoretech.user.repository.UserInfoRepository;
import com.linkmoretech.user.service.LicensePlateService;
import com.linkmoretech.user.vo.LicensePlateAddRequest;
import com.linkmoretech.user.vo.LicensePlateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * @Description:
 * @date: 下午8:41 2019/4/10
 */
@Service
@Slf4j
public class LicensePlateServiceImpl implements LicensePlateService {

    @Autowired
    LicensePlateRepository licensePlateRepository;

    @Autowired
    LeaseUserComponent leaseUserComponent;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    LeaseUserRepository leaseUserRepository;

    private final int PLATE_MAX = 3;

    @Override
    public void createPlate(LicensePlateAddRequest licensePlateAddRequest) throws CommonException {
        /**
         * 创建车牌号
         * 检测车牌号是否存在 如果存在不添加
         * 查询当前用户下的车牌号列表，如果列表数大于3个不添加
         * */
        LicensePlate licensePlate = licensePlateRepository.findByPlateNo(licensePlateAddRequest.getPlateNo());
        if (licensePlate != null) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "车牌号已存在");
        }
        List<LicensePlate> userLicensePlate = licensePlateRepository.findAllByUserId(licensePlateAddRequest.getUserId());
        log.info("用户 {} 已添加 {} 个车牌号", licensePlateAddRequest.getUserId(), userLicensePlate.size());
        if (userLicensePlate.size() == PLATE_MAX) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "车牌号不能超过 " + PLATE_MAX + "个");
        }
        LicensePlate licensePlateEntity = new LicensePlate();
        BeanUtils.copyProperties(licensePlateAddRequest, licensePlateEntity);
        LicensePlate resultLicensePlate = licensePlateRepository.save(licensePlateEntity);
        Set<String> leaseCodeSet = leaseUserComponent.getLeaseCode(licensePlateAddRequest.getPlateNo());
        if (leaseCodeSet != null && leaseCodeSet.size() > 0) {
            UserInfo userInfo = userInfoRepository.findById(resultLicensePlate.getUserId()).get();
            /**
             * 创建长租记录
             * */
            List<LeaseUser> leaseUserList = leaseCodeSet.stream().map(leaseCode -> {
                LeaseUser leaseUser = new LeaseUser();
                leaseUser.setLeaseCode(leaseCode);
                leaseUser.setUserId(userInfo.getId());
                leaseUser.setMobile(userInfo.getUserMobile());
                return leaseUser;
            }).collect(Collectors.toList());
            leaseUserRepository.saveAll(leaseUserList);
        }
        log.info("add plate success for id {}" , resultLicensePlate.getId());
    }

    @Override
    public List<LicensePlateResponse> findPlateByUserId(String userId) {
        List<LicensePlate> userLicensePlate = licensePlateRepository.findAllByUserId(userId);
        List<LicensePlateResponse> plateList = new ArrayList<>();
        userLicensePlate.forEach(licensePlate -> {
            LicensePlateResponse plateResponse = new LicensePlateResponse();
            BeanUtils.copyProperties(licensePlate, plateResponse);
            plateList.add(plateResponse);
        });
        return plateList;
    }

    @Override
    public void removePlate(String userId, String plateNo) throws CommonException {
        List<LicensePlate> plateList = licensePlateRepository.removeByPlateNoAndUserId(plateNo, userId);
        if (plateList.size() == 0) {
            throw new CommonException(ResponseCodeEnum.ERROR, "未找到对应记录");
        }
        /**
         * 删除长租用户对应车牌信息
         * */
        Set<String> leaseCodeSet = leaseUserComponent.getLeaseCode(plateNo);
        if (leaseCodeSet != null && leaseCodeSet.size() > 0) {
            /**
             * 删除长租用户记录
             * */
            log.warn("需要删除长租记录 {}" , leaseCodeSet);
        }
        licensePlateRepository.flush();
    }
}
