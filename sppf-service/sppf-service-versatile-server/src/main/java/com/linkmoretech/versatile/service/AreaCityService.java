package com.linkmoretech.versatile.service;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.versatile.vo.request.AreaCityCreateRequest;
import com.linkmoretech.versatile.vo.response.AreaCityListResponse;
import com.linkmoretech.versatile.vo.response.AreaCityTreeResponse;

import java.util.List;

/**
 * 城市区域服务层
 * @Author: alec
 * @Description:
 * @date: 8:32 PM 2019/4/29
 */
public interface AreaCityService {

    /**
     * 创建城市参数
     * @param areaCityCreateRequest 城市参数
     * @throws CommonException 自定义响应异常
     * */
    void create(AreaCityCreateRequest areaCityCreateRequest) throws CommonException;

    /**
     * 根据父ID查询子区域列表
     * @param id 父ID
     * @return 城市区域列表
     * */
    List<AreaCityListResponse> list(Long id);

    /**
     * 删除城市区域
     * @param id ID
     * */
    void delete(Long id);


    /**
     * 查询城市下拉树
     * @return 城市下拉数据
     * */
    List<AreaCityTreeResponse> tree();

    /**
     * 根据城市编码查询父节点
     * @param cityCode 城市编码
     * @return 所有城市编码
     * */
    List<String> getAllCityCode(String cityCode);

    /**
     * 根据城市编码查询城市名称
     * @param cityCode 城市编码
     * @return 城市名称
     * */
    String getCityName(String cityCode);
}
