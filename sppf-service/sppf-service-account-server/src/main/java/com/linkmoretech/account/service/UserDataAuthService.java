package com.linkmoretech.account.service;

import com.linkmoretech.account.vo.request.AuthParkAddRequest;
import com.linkmoretech.account.vo.request.SearchRequest;
import com.linkmoretech.account.vo.response.AuthDataListResponse;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 16:34 2019-06-24
 */
public interface UserDataAuthService {

    /**
     * 添加车场数据
     * @param authParkAddRequest 车场授权参数
     * */
    void addParkAuth(AuthParkAddRequest authParkAddRequest);


    /**
     * 列表
     * @param pageSearchRequest 查询条件
     * @return 分页数据集合
     * */
    PageDataResponse<AuthDataListResponse> list (PageSearchRequest<Long> pageSearchRequest);


    /**
     * 根据用户ID 获取其操作车场权限
     * */
    List<Long> getParkIdList(Long userId);


    /**
     * 根据用户ID 获取其操作车位权限
     * */
    List<String> getPlaceNoList(Long userId, Long parkId);

}
