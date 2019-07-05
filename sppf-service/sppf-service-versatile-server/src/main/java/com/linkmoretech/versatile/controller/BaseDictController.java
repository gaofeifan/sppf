package com.linkmoretech.versatile.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.service.BaseDictService;
import com.linkmoretech.versatile.vo.request.BaseDictCreateRequest;
import com.linkmoretech.versatile.vo.request.BaseDictEditRequest;
import com.linkmoretech.versatile.vo.response.BaseDictPageResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 数据字典controller
 * @Author: jhb
 * @Description:
 * @date: 10:27 AM 2019/4/30
 */
@RestController(value = "baseDict")
@RequestMapping("dict")
@Api(tags = "数据字典", value = "Base-Dict")
public class BaseDictController {

    @Autowired
    BaseDictService baseDictService;

    @ApiOperation(value = "添加数据字典", notes = "添加数据字典")
    @PostMapping(value = "create")
    public void create(@RequestBody @Valid BaseDictCreateRequest baseDictCreateRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        baseDictService.create(baseDictCreateRequest);
    }

    @ApiOperation(value = "编辑数据字典", notes = "编辑数据字典")
    @PostMapping(value = "edit")
    public void edit(@RequestBody @Valid BaseDictEditRequest baseDictEditRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        baseDictService.edit(baseDictEditRequest);
    }

    @ApiOperation(value = "删除数据字典", notes = "删除数据字典")
    @DeleteMapping(value = "delete/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        baseDictService.delete(id);
    }

    @ApiOperation(value = "获取车场列表", notes = "用于分页显示已添加车场的数据")
    @PostMapping(value = "list")
    public PageDataResponse<BaseDictPageResponse> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                                    BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return baseDictService.searchPage(searchRequest);
    }


}
