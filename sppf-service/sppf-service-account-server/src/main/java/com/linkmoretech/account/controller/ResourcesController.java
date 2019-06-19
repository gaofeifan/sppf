package com.linkmoretech.account.controller;

import com.linkmoretech.account.component.ResourcesComponent;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 16:48 2019-05-29
 */
@RestController(value = "ResourcesController")
@RequestMapping(value = "resources")
public class ResourcesController {

    @Autowired
    ResourcesService resourcesService;

    @Autowired
    ResourcesComponent resourcesComponent;

    @PostMapping(value = "create")
    public void create(@RequestBody ResourcesCreateRequest resourcesCreateRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        resourcesService.createResource(resourcesCreateRequest);
    }

    @PostMapping(value = "update")
    public void update(@RequestBody ResourceEditRequest resourceEditRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        resourcesService.update(resourceEditRequest);
    }

    @PostMapping(value = "list")
    public PageDataResponse<ResourcesListResponse> list(@RequestBody  PageSearchRequest<SearchRequest> pageRequest,
                                                        BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return resourcesService.searchList(pageRequest);
    }

    @DeleteMapping(value = "delete")
    public void delete(@RequestParam(value = "resourceId") Long resourceId) throws CommonException {
        resourcesService.remove(resourceId);
    }

    @PutMapping(value = "enable")
    public void enable(@RequestParam(value = "resourceId") Long resourceId, @RequestParam(value = "status")
            Integer status) throws CommonException {
        resourcesService.updateStatus(resourceId, status);
    }

    @GetMapping(value = "all")
    public List<ResourcesAllResponse> getAllResources(@RequestParam(value = "clientId") String clientId) {
        return resourcesComponent.getResource(clientId);
    }

    @GetMapping(value = "find")
    public ResourceResponse detail(@RequestParam(value = "id") Long id) throws CommonException {
        return resourcesService.resourceInfo(id);
    }
}
