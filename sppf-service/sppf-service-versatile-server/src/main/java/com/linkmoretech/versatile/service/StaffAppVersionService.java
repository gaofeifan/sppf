package com.linkmoretech.versatile.service;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.vo.request.StaffAppVersionCreateRequest;
import com.linkmoretech.versatile.vo.request.StaffAppVersionEditRequest;
import com.linkmoretech.versatile.vo.request.StaffAppVersionRequest;
import com.linkmoretech.versatile.vo.response.StaffAppVersionPageResponse;
import com.linkmoretech.versatile.vo.response.StaffAppVersionResponse;

/**
 * 管理版版本管理服务层
 * @Author: alec
 * @Description:
 * @date: 8:32 PM 2019/4/29
 */
public interface StaffAppVersionService {

    /**
     * 创建版本管理参数
     * @param staffAppVersionCreateRequest 城市参数
     * @throws CommonException 自定义响应异常
     * */
    void create(StaffAppVersionCreateRequest staffAppVersionCreateRequest) throws CommonException;

    /**
     * 更新版本管理参数
     * @param staffAppVersionEditRequest 城市参数
     * @throws CommonException 自定义响应异常
     * */
    void edit(StaffAppVersionEditRequest staffAppVersionEditRequest) throws CommonException;

    /**
     * 删除版本管理
     * @param id ID
     * */
    void delete(Long id);
    
    /**
     * 分页查询列表
     * @param pageSearchRequest 分页参数
     * @return 分页查询结果
     * */
    PageDataResponse<StaffAppVersionPageResponse> searchPage(PageSearchRequest pageSearchRequest);
    
    /**
     * 查询当前最新版本
     * @param appType
     * @return
     */
	StaffAppVersionResponse currentAppVersion(int appType);

	void report(StaffAppVersionRequest staffAppVersionRequest, Long userId);

}
