package com.linkmoretech.versatile.service;

import java.util.List;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.vo.request.BaseDictCreateRequest;
import com.linkmoretech.versatile.vo.request.BaseDictEditRequest;
import com.linkmoretech.versatile.vo.response.BaseDictPageResponse;
import com.linkmoretech.versatile.vo.response.BaseDictResponse;

/**
 * 城市区域服务层
 * @Author: alec
 * @Description:
 * @date: 8:32 PM 2019/4/29
 */
public interface BaseDictService {

    /**
     * 创建数据字典分类参数
     * @param baseDictCreateRequest 城市参数
     * @throws CommonException 自定义响应异常
     * */
    void create(BaseDictCreateRequest baseDictCreateRequest) throws CommonException;

    /**
     * 更新数据字典分类参数
     * @param baseDictEditRequest 城市参数
     * @throws CommonException 自定义响应异常
     * */
    void edit(BaseDictEditRequest baseDictEditRequest) throws CommonException;

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
    PageDataResponse<BaseDictPageResponse> searchPage(PageSearchRequest pageSearchRequest);

	List<BaseDictResponse> findListByCode(String code);

}
