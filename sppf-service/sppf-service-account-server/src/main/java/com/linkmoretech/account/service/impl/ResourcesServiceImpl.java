package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.entity.Resources;
import com.linkmoretech.account.enums.EnableStatusEnum;
import com.linkmoretech.account.enums.ResourceTypeEnum;
import com.linkmoretech.account.resposity.ResourcesRepository;
import com.linkmoretech.account.service.ResourcesService;
import com.linkmoretech.account.vo.request.ResourceEditRequest;
import com.linkmoretech.account.vo.request.SearchRequest;
import com.linkmoretech.account.vo.request.ResourcesCreateRequest;
import com.linkmoretech.account.vo.response.ResourceResponse;
import com.linkmoretech.account.vo.response.ResourcesAllResponse;
import com.linkmoretech.account.vo.response.ResourcesListResponse;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description:
 * @date: 16:17 2019-05-29
 */
@Service
public class ResourcesServiceImpl implements ResourcesService {

    @Autowired
    ResourcesRepository resourcesRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createResource(ResourcesCreateRequest resourcesCreateRequest) throws CommonException {
        Resources resource = resourcesRepository.findByResourceName(resourcesCreateRequest.getResourceName());
        if (resource != null && resource.getClientId().equals(resourcesCreateRequest.getClientId())) {
            throw new CommonException(ResponseCodeEnum.ERROR, "资源名称已存在");
        }
        resource = new Resources();
        Date currentDate = new Date();
        BeanUtils.copyProperties(resourcesCreateRequest, resource);
        if (resource.getParentId() != null) {
            Optional<Resources> resources = resourcesRepository.findById(resource.getParentId());
            if (resources.isPresent()) {
                Resources parentResource = resources.get();
                resource.setResourceCode(parentResource.getResourceCode() + resource.getResourceCode());
                resource.setClientId(parentResource.getClientId());
            }
        }
        /**默认资源启用*/
        if (resourcesCreateRequest.getResourceType() == null) {
            resource.setResourceType(ResourceTypeEnum.MENU.getCode());
        }
        resource.setResourceStatus(EnableStatusEnum.ENABLED.getCode());
        resource.setCreateTime(currentDate);
        resource.setUpdateTime(currentDate);
        resourcesRepository.save(resource);
    }

    @Override
    public PageDataResponse<ResourcesListResponse> searchList(PageSearchRequest<SearchRequest> pageSearchRequest) {
        String[] sortField = new String[]{"resourceCode", "resourceSort"};
        Sort sort = Sort.by(Sort.Direction.ASC, sortField);
        Pageable pageable  = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest
                .getPageSize(), sort);
        Resources searchResource = new Resources();
        searchResource.setClientId(pageSearchRequest.getCondition().getClientId());
        Example<Resources> example = Example.of(searchResource);
        Page<Resources> page = resourcesRepository.findAll(example, pageable);
        PageDataResponse<ResourcesListResponse> pageDataResponse = new PageDataResponse<>();
        pageDataResponse.setTotal(page.getTotalElements());
        List<Resources> resourcesList = page.getContent();
        List<ResourcesListResponse> resourcesListResponseList = resourcesList.stream().map(resources -> {
            ResourcesListResponse resourcesListResponse = new ResourcesListResponse();
            BeanUtils.copyProperties(resources, resourcesListResponse);
            return resourcesListResponse;
        }).collect(Collectors.toList());
        pageDataResponse.setData(resourcesListResponseList);
        return pageDataResponse;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(Long id) throws CommonException {
        Optional<Resources> resources = resourcesRepository.findById(id);
        if (!resources.isPresent()) {
            throw new CommonException(ResponseCodeEnum.ERROR, "资源不存在");
        }
        resourcesRepository.deleteById(id);
        resourcesRepository.deleteAllByParentId(id);
    }

    @Override
    public void updateStatus(Long id, Integer status) throws CommonException {
        Optional<Resources> resources = resourcesRepository.findById(id);
        if (!resources.isPresent()) {
            throw new CommonException(ResponseCodeEnum.ERROR, "资源不存在");
        }
        EnableStatusEnum enableStatusEnum = EnableStatusEnum.getStatus(status);
        if (enableStatusEnum == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "状态码不合法");
        }
        resourcesRepository.updateEnableStatus(id, enableStatusEnum.getCode());
    }

    @Override
    public List<ResourcesAllResponse> getAllResources(String clientId) {
        return null;
    }

    @Override
    public ResourceResponse resourceInfo(Long id) throws CommonException {

        Optional<Resources> optional = resourcesRepository.findById(id);
        if (!optional.isPresent()) {
            throw new CommonException(ResponseCodeEnum.ERROR, "资源不存在");
        }
        Resources resources = optional.get();
        ResourceResponse resourceResponse = new ResourceResponse();
        BeanUtils.copyProperties(resources, resourceResponse);

        return resourceResponse;
    }

    @Override
    public void update(ResourceEditRequest resourceEditRequest) throws CommonException {

        Optional<Resources> optional = resourcesRepository.findById(resourceEditRequest.getId());
        if (!optional.isPresent()) {
            throw new CommonException(ResponseCodeEnum.ERROR, "资源不存在");
        }
        Resources resources = optional.get();
        resources.setResourceName(resourceEditRequest.getResourceName());
        resources.setRouterName(resourceEditRequest.getRouterName());
        resources.setResourceSort(resourceEditRequest.getResourceSort());
        resourcesRepository.save(resources);
    }


}
