package com.linkmoretech.versatile.service;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.vo.request.BaseDictGroupCreateRequest;
import com.linkmoretech.versatile.vo.request.BaseDictGroupEditRequest;
import com.linkmoretech.versatile.vo.response.AreaCityTreeResponse;
import com.linkmoretech.versatile.vo.response.BaseDictGroupPageResponse;
import com.linkmoretech.versatile.vo.response.BaseDictGroupTreeResponse;

import java.util.List;

/**
 * 数据字典分类服务层
 * @Author: jhb
 * @Description:
 * @date: 8:32 PM 2019/4/29
 */
public interface BaseDictGroupService {

    /**
     * 创建数据字典分类参数
     * @param baseDictGroupCreateRequest 城市参数
     * @throws CommonException 自定义响应异常
     * */
    void create(BaseDictGroupCreateRequest baseDictGroupCreateRequest) throws CommonException;

    /**
     * 更新数据字典分类参数
     * @param baseDictGroupEditRequest 城市参数
     * @throws CommonException 自定义响应异常
     * */
    void edit(BaseDictGroupEditRequest baseDictGroupEditRequest) throws CommonException;

    /**
     * 删除数据字典分类
     * @param id ID
     * */
    void delete(Long id);
    
    /**
     * 分页查询列表
     * @param pageSearchRequest 分页参数
     * @return 分页查询结果
     * */
    PageDataResponse<BaseDictGroupPageResponse> searchPage(PageSearchRequest pageSearchRequest);

    /**
     * 查询城市下拉树
     * @return 城市下拉数据
     * */
    List<BaseDictGroupTreeResponse> tree();
}
