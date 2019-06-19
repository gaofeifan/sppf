package com.linkmoretech.account.controller;

import com.linkmoretech.account.service.RolesService;
import com.linkmoretech.account.vo.request.RolesAddResourceRequest;
import com.linkmoretech.account.vo.request.RolesCreateRequest;
import com.linkmoretech.account.vo.request.RolesEditRequest;
import com.linkmoretech.account.vo.response.RolesListResponse;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: alec
 * Description: 角色Controller
 * @date: 14:56 2019-05-30
 */
@RestController(value = "RolesController")
@RequestMapping(value = "roles")
public class RolesController {

    @Autowired
    RolesService rolesService;

    @PostMapping(value = "create")
    public Long create(@RequestBody RolesCreateRequest rolesCreateRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return rolesService.create(rolesCreateRequest);
    }

    @GetMapping(value = "list")
    public List<RolesListResponse> list(@RequestParam(value = "clientId") String clientId) {
        return rolesService.list(clientId);
    }

    @GetMapping(value = "all-list")
    public Map<String, List<RolesListResponse>> list() {
        return rolesService.getRolesGroup();
    }

    @GetMapping(value = "resources-list")
    public List<Long> resourcesList(@RequestParam(value = "rolesId") Long rolesId) {
        return rolesService.getResourcesIdByRolesId(rolesId);
    }

    @PostMapping(value = "auth")
    public void authResource(@RequestBody RolesAddResourceRequest rolesAddResourceRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        rolesService.addResourcesForRoles(rolesAddResourceRequest);
    }

    @PostMapping(value = "update")
    public Long update(@RequestBody RolesEditRequest rolesEditRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return rolesService.update(rolesEditRequest);
    }

    @DeleteMapping(value = "remove")
    public void remove(@RequestParam(value = "rolesId") Long rolesId) throws CommonException {
        rolesService.remove(rolesId);
    }
}
