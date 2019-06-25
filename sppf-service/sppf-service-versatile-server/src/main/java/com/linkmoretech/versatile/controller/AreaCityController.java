package com.linkmoretech.versatile.controller;

import com.linkmoretech.auth.common.util.AuthenticationTokenAnalysis;
import com.linkmoretech.common.annation.IgnoreResponseAdvice;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.versatile.service.AreaCityService;
import com.linkmoretech.versatile.vo.request.AreaCityCreateRequest;
import com.linkmoretech.versatile.vo.response.AreaCityListResponse;
import com.linkmoretech.versatile.vo.response.AreaCityTreeResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * 城市区域controller
 * @Author: alec
 * @Description:
 * @date: 10:27 AM 2019/4/30
 */
@RestController(value = "areaCity")
@RequestMapping("city")
@Slf4j
public class AreaCityController {

    @Autowired
    AreaCityService areaCityService;


    @ApiOperation(value = "添加城市", notes = "添加城市")
    @PostMapping(value = "create")
    public void create(@RequestBody @Valid AreaCityCreateRequest areaCityCreateRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        areaCityService.create(areaCityCreateRequest);
    }

    @ApiOperation(value = "城市列表", notes = "城市列表")
    @GetMapping(value = "list")
   // @PreAuthorize(value = "hasAuthority('query-demo')")
    public List<AreaCityListResponse> list(Authentication authentication,
                                           @RequestParam(value = "parentId", required = false) Long parentId) {

        AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
        log.info("登录用户 {}", authenticationTokenAnalysis.getUsername());
        log.info("客户端ID {}", authenticationTokenAnalysis.getClientId());
        log.info("用户ID {}", authenticationTokenAnalysis.getUserId());
        return areaCityService.list(parentId);
    }

    @ApiOperation(value = "删除城市", notes = "城市列表")
    @DeleteMapping(value = "delete/{id}/data")
    public void delete(@PathVariable(value = "id") Long id) {
        areaCityService.delete(id);
    }


    @ApiOperation(value = "获取城市树形列表", notes = "获取城市树形列表")
    @GetMapping(value = "tree")
    public List<AreaCityTreeResponse> tree() {
        return areaCityService.tree();
    }

    @GetMapping(value = "/allCityCode")
    @IgnoreResponseAdvice
    public List<String> getAllCityCode(@RequestParam(value = "cityCode") String cityCode){
        return areaCityService.getAllCityCode(cityCode);
    }

    @GetMapping(value = "/getCityName")
    @IgnoreResponseAdvice
    public String getCityName(@RequestParam(value = "cityCode") String cityCode){
        return areaCityService.getCityName(cityCode);
    }
}
