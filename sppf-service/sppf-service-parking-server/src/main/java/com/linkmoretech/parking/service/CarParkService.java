package com.linkmoretech.parking.service;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.entity.CarPark;
import com.linkmoretech.parking.vo.request.CarParkCreateRequest;
import com.linkmoretech.parking.vo.request.CarParkEditRequest;
import com.linkmoretech.parking.vo.request.CarParkLineRequest;
import com.linkmoretech.parking.vo.response.*;
import reactor.ipc.netty.http.server.HttpServerRequest;

import java.util.List;

import org.springframework.security.core.Authentication;

/**
 * 车场业务逻辑层
 * @Author: alec
 * @Description:
 * @date: 5:21 PM 2019/4/18
 */
public interface CarParkService {

    /**
     * 创建车场
     * @param carParkCreateRequest 创建车场参数
     * @throws CommonException 认证异常
     * */
    void create(CarParkCreateRequest carParkCreateRequest) throws CommonException;

    /**
     * 分页查询列表
     * @param pageSearchRequest 分页参数
     * @return 分页查询结果
     * */
    PageDataResponse<CarParkPageResponse> searchPage(PageSearchRequest pageSearchRequest);

    /**
     * 查询车场明细数据
     * @param carParkId 车场ID
     * @return 车场明细数据
     * @throws CommonException 认证异常
     * */
    CarParkInfoResponse findDetail(Long carParkId) throws CommonException;

    /**
     * 删除车场
     * @param parkId 车场ID
     * @throws CommonException 认证异常
     * */
    void remove(Long parkId) throws CommonException;

    /**
     * 查询车场下拉框数据，根据城市编码
     * @param cityCode 城市编码
     * @return 按城市名称分布的车场
     * @throws CommonException
     * */
    List<CarParkSelectResponse> getSelectedData(String cityCode) ;

    /**
     * 编辑车场信息查询
     * @param carParkId 车场ID
     * @return 编辑车场信息
     * @throws CommonException
     * */
    CarParkEditResponse findEditInfo(Long carParkId) throws CommonException;

    /**
     * 编辑车场信息
     * @param carParkEditRequest 车场创建信息
     * @throws CommonException
     * */
    void edit(CarParkEditRequest carParkEditRequest) throws CommonException;

    /**
     * 上线/下线车位
     * @param carParkLineRequest 更新参数
     * @throws CommonException 自定义异常
     * */
    void upDownLine(CarParkLineRequest carParkLineRequest) throws CommonException;

    /**
     * @Author GFF
     * @Description 根据分组编号查询
     * @Date 2019/6/19
     */
    CarPark findByGateway(String groupCode);


	List<CityParkListResponse> carParkList(Authentication authentication);
}
