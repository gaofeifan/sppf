package com.linkmoretech.versatile.service.impl;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.entity.BaseDict;
import com.linkmoretech.versatile.repository.BaseDictRepository;
import com.linkmoretech.versatile.service.BaseDictService;
import com.linkmoretech.versatile.vo.request.BaseDictCreateRequest;
import com.linkmoretech.versatile.vo.request.BaseDictEditRequest;
import com.linkmoretech.versatile.vo.response.BaseDictPageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jhb
 * @Description:
 * @date: 8:36 PM 2019/4/29
 */
@Service
@Slf4j
public class BaseDictServiceImpl implements BaseDictService {

    @Autowired
    BaseDictRepository baseDictRepository;

    @Override
    public void create(BaseDictCreateRequest baseDictCreateRequest) throws CommonException {
        BaseDict baseDict = new BaseDict();
        BeanUtils.copyProperties(baseDictCreateRequest, baseDict);
        baseDict.setCreateTime(new Date());
        baseDict.setUpdateTime(new Date());
        baseDictRepository.save(baseDict);
    }

    @Override
    public void edit(BaseDictEditRequest baseDictEditRequest) throws CommonException {
        BaseDict baseDict = new BaseDict();
        BeanUtils.copyProperties(baseDictEditRequest, baseDict);
        baseDict.setCreateTime(new Date());
        baseDict.setUpdateTime(new Date());
        baseDictRepository.saveAndFlush(baseDict);
    }

    @Override
    public void delete(Long id) {
        baseDictRepository.deleteById(id);
    }

    @Override
    public PageDataResponse<BaseDictPageResponse> searchPage(PageSearchRequest pageSearchRequest) {
        Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest.getPageSize());
        Page<BaseDict> baseDictPage = baseDictRepository.findAll(pageable);
        PageDataResponse<BaseDictPageResponse> pageDataResponse = new PageDataResponse<>();
        pageDataResponse.setTotal(baseDictPage.getTotalElements());
        List<BaseDict> baseDictList = baseDictPage.getContent();
        List<BaseDictPageResponse> baseDictPageResponses = baseDictList.stream().map(baseDict -> {
            BaseDictPageResponse baseDictPageResponse = new BaseDictPageResponse();
            BeanUtils.copyProperties(baseDict, baseDictPageResponse);
            return baseDictPageResponse;
        }).collect(Collectors.toList());
        pageDataResponse.setData(baseDictPageResponses);
        return pageDataResponse;
    }
}
