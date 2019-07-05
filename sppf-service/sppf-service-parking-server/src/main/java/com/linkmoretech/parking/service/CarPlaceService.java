package com.linkmoretech.parking.service;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.common.PlaceParkIdAndRangeInput;
import com.linkmoretech.parking.common.PlaceParkIdAndRangeOutput;
import com.linkmoretech.parking.entity.CarPlace;
import com.linkmoretech.parking.vo.request.CarPlaceCreateRequest;
import com.linkmoretech.parking.vo.request.CarPlaceEditRequest;
import com.linkmoretech.parking.vo.request.CarPlaceListRequest;
import com.linkmoretech.parking.vo.response.*;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * @Author: alec
 * @Description:
 * @date: 8:50 PM 2019/5/7
 */
public interface CarPlaceService {


    /**
     * 创建车位
     * @param carPlaceCreateRequest 创建车位入参
     * @throws CommonException 自定义异常处理
     * */
    void create(CarPlaceCreateRequest carPlaceCreateRequest) throws CommonException;

    /**
     * 查询车位列表分页
     * @param pageSearchRequest 查询条件
     * @return 带分页格式的数据
     * */
    PageDataResponse<CarPlacePageResponse> searchPage(PageSearchRequest pageSearchRequest);

    /**
     * 根据车场查询车场可用车位列表
     * 可用条件 空闲 - 临停 - 上线
     * @param carParkId 车场ID
     * @return 返回 ID name 格式数据
     * */
    List<CarPlaceSelectedResponse> getSelectedData(Long carParkId);

    /**
     * 删除车位
     * @param carPlaceId 车位ID
     * @throws CommonException 自定义异常处理
     * */
    void remove(Long carPlaceId) throws CommonException;

    /**
     * 获取车位详情
     * @param carPlaceId 车位ID
     * @return 车位详情
     * @throws CommonException 自定义异常处理
     * */
    CarPlaceInfoResponse findDetail(Long carPlaceId) throws CommonException;

    /**
     * 查询车位编辑数据
     @ param carPlaceId 车位ID
     * @return 车位详情
     * @throws CommonException 自定义异常处理
     * */
    CarPlaceEditResponse findEditInfo(Long carPlaceId) throws CommonException;

    /**
     * 编辑车位
     * @param carPlaceEditRequest 编辑车位
     * @throws CommonException 自定义异常
     * */
    void edit(CarPlaceEditRequest carPlaceEditRequest) throws CommonException;

    /**
     * 上线/下线车位
     * @param id 车位ID
     * @param lineStatus 上下线状态
     * @throws CommonException 自定义异常
     * */
    void upDownLine(Long id, Integer lineStatus) throws CommonException;
    /**
     * @Author GFF
     * @Description  根据车场id批量查询
     * @Date 2019/6/6
     */
    List<CarPlace> findCarPlaceByParkIds(List<Long> parkIds);


    CarPlaceDetailsResponse details(HttpServletRequest request, Long carPlaceId);

    public CarPlaceDetailsSnResponse detailsSn(HttpServletRequest request, String sn, Long parkId);

    CarPlace findByPlaceNoAndParkIdAndFloorPlanId(String stallName, Long preId, Long floorId);

    void insert(CarPlace stall);


    void updateLockCode(Long id, String lockCode);

    List<CarPalceListResponse> findCarPlaceListByParkId(HttpServletRequest request, CarPlaceListRequest carPlace, Authentication authentication);

    List<PlaceParkIdAndRangeOutput> findByParkIdAndIdRange(PlaceParkIdAndRangeInput input) throws CommonException;

	void updateLockStatusAndPlaceStatus(String lockCode, Integer lockStatus, Integer placeStatus);

	CarPlace findByLockCode(String lockCode);



}
