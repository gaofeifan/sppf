package com.linkmoretech.user.service;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.user.vo.LicensePlateAddRequest;
import com.linkmoretech.user.vo.LicensePlateResponse;

import java.util.List;

/**
 * 车牌号业务层
 * @Author: alec
 * @Description:
 * @date: 下午8:36 2019/4/10
 */
public interface LicensePlateService {

    /**
     * 创建车牌号
     * @param licensePlateAddRequest 车牌号参数
     * */
    void createPlate(LicensePlateAddRequest licensePlateAddRequest) throws CommonException;

    /**
     * 根据用户ID查询用户车牌号列表
     * @param userId 用户ID
     * @return 车牌号列表
     * */
    List<LicensePlateResponse> findPlateByUserId(String userId);

    /**
     * 删除车牌号
     * @param userId 用户ID
     * @param plateNo 车牌号
     * */
    void removePlate(String userId, String plateNo) throws CommonException;
}
