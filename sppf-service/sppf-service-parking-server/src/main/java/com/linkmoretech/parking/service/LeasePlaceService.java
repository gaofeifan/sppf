package com.linkmoretech.parking.service;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.common.LeaseInput;
import com.linkmoretech.parking.common.LeaseOutput;
import com.linkmoretech.parking.vo.request.LeasePlaceBatchRequest;
import com.linkmoretech.parking.vo.request.LeasePlaceCreateRequest;
import com.linkmoretech.parking.vo.request.LeasePlaceEditRequest;
import com.linkmoretech.parking.vo.response.LeasePlaceEditResponse;
import com.linkmoretech.parking.vo.response.LeasePlaceInfoResponse;
import com.linkmoretech.parking.vo.response.LeasePlaceListResponse;

/**
 * @Author: alec
 * Description: 长租车位服务接口
 * @date: 17:16 2019-05-08
 */
public interface LeasePlaceService {

    /**
     * @param pageSearchRequest 查询条件
     * @return 分页数据
     * */
    PageDataResponse<LeasePlaceListResponse> searchPage(PageSearchRequest pageSearchRequest);

    /**
     * 创建长租车位
     * @param leasePlaceCreateRequest 创建长租车位参数
     * @throws CommonException 自定义异常
     * */
    void create(LeasePlaceCreateRequest leasePlaceCreateRequest) throws CommonException;

    /**
     * 更新长租车位
     * @param leasePlaceEditRequest 创建长租车位参数
     * @throws CommonException 自定义异常
     * */
    void update(LeasePlaceEditRequest leasePlaceEditRequest) throws CommonException;


    /**
     * 批量创建长租车位
     * @param leasePlaceBatchRequest 批量导入参数
     * @throws CommonException 验证自定义异常
     * */
    void batchImport(LeasePlaceBatchRequest leasePlaceBatchRequest) throws CommonException;

    /**
     * 删除长租车位
     * @param leaseId 当前长租记录ID
     * @param username 操作人账号
     * @throws CommonException 验证自定义异常
     * */
    void removeId( Long leaseId, String username) throws CommonException;


    /**
     * 根据车牌号查询其长租车位信息
     * @param leaseInput 车牌号
     * @return 长租车位信息
     * */
    LeaseOutput getLeasePlaceList(LeaseInput leaseInput);


    /**
     * 启用/禁用长租车位
     * @param leaseId 长租记录ID
     * @param status 状态码
     * @param username 更新人
     * @throws CommonException 自定义验证异常
     * */
    void updateEnableStatus(Long leaseId, Integer status, String username) throws CommonException;


    /**
     * 查询当前车位的长租情况
     * @param id 记录ID
     * @return 返回数据域
     * */
    LeasePlaceInfoResponse getLeasePlaceDetail(Long id) throws CommonException;

    /**
     * 根据长租授权码查询编辑长租车位信息
     * */
    LeasePlaceEditResponse getDetail(String leaseCode) throws CommonException;
}
