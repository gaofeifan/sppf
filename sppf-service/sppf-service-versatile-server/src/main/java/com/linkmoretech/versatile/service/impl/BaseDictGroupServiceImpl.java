package com.linkmoretech.versatile.service.impl;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.entity.BaseDictGroup;
import com.linkmoretech.versatile.repository.BaseDictGroupRepository;
import com.linkmoretech.versatile.service.BaseDictGroupService;
import com.linkmoretech.versatile.vo.request.BaseDictGroupCreateRequest;
import com.linkmoretech.versatile.vo.request.BaseDictGroupEditRequest;
import com.linkmoretech.versatile.vo.response.BaseDictGroupPageResponse;
import com.linkmoretech.versatile.vo.response.BaseDictGroupTreeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class BaseDictGroupServiceImpl implements BaseDictGroupService {

    @Autowired
    BaseDictGroupRepository baseDictGroupRepository;

    @Override
    public void create(BaseDictGroupCreateRequest baseDictGroupCreateRequest) throws CommonException {
        BaseDictGroup baseDictGroup = new BaseDictGroup();
        BeanUtils.copyProperties(baseDictGroupCreateRequest, baseDictGroup);
        baseDictGroup.setCreateTime(new Date());
        baseDictGroupRepository.save(baseDictGroup);
    }

    @Override
    public void edit(BaseDictGroupEditRequest baseDictGroupEditRequest) throws CommonException {
        BaseDictGroup baseDictGroup = new BaseDictGroup();
        BeanUtils.copyProperties(baseDictGroupEditRequest, baseDictGroup);
        baseDictGroup.setCreateTime(new Date());
        baseDictGroupRepository.saveAndFlush(baseDictGroup);
    }

    @Override
    public void delete(Long id) {
        baseDictGroupRepository.deleteById(id);
    }

    @Override
    public PageDataResponse<BaseDictGroupPageResponse> searchPage(PageSearchRequest pageSearchRequest) {
        Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest.getPageSize());
        Page<BaseDictGroup> baseDictGroupPage = baseDictGroupRepository.findAll(pageable);
        PageDataResponse<BaseDictGroupPageResponse> pageDataResponse = new PageDataResponse<>();
        pageDataResponse.setTotal(baseDictGroupPage.getTotalElements());
        List<BaseDictGroup> baseDictGroupList = baseDictGroupPage.getContent();
        List<BaseDictGroupPageResponse> baseDictGroupPageResponses = baseDictGroupList.stream().map(baseDictGroup -> {
            BaseDictGroupPageResponse baseDictGroupPageResponse = new BaseDictGroupPageResponse();
            BeanUtils.copyProperties(baseDictGroup, baseDictGroupPageResponse);
            return baseDictGroupPageResponse;
        }).collect(Collectors.toList());
        pageDataResponse.setData(baseDictGroupPageResponses);
        return pageDataResponse;
    }

    @Override
    public List<BaseDictGroupTreeResponse> tree() {
        List<BaseDictGroup> baseDictGroupList = baseDictGroupRepository.findAll(Sort.by(Sort.Order.asc("orderIndex")));
        List<BaseDictGroupTreeResponse> baseDictGroupTreeResponses = baseDictGroupList.stream().map(baseDictGroup -> {
            BaseDictGroupTreeResponse baseDictGroupTreeResponse = new BaseDictGroupTreeResponse();
            baseDictGroupTreeResponse.setId(baseDictGroup.getId());
            baseDictGroupTreeResponse.setLabel(baseDictGroup.getName());
            baseDictGroupTreeResponse.setValue(baseDictGroup.getCode());
            return baseDictGroupTreeResponse;
        }).collect(Collectors.toList());
        return baseDictGroupTreeResponses;
    }
}
