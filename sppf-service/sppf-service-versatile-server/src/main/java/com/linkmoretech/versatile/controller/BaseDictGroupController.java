package com.linkmoretech.versatile.controller;

import static com.linkmoretech.common.enums.ResponseCodeEnum.PARAMS_ERROR;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.service.BaseDictGroupService;
import com.linkmoretech.versatile.vo.request.BaseDictGroupCreateRequest;
import com.linkmoretech.versatile.vo.request.BaseDictGroupEditRequest;
import com.linkmoretech.versatile.vo.response.BaseDictGroupPageResponse;
import com.linkmoretech.versatile.vo.response.BaseDictGroupTreeResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

/**
 * 字典分类controller
 * @Author: jhb
 * @Description:
 * @date: 10:27 AM 2019/4/30
 */
@RestController(value = "baseDictGroup")
@RequestMapping("dict-group")
@Api(tags = "数据字典分类", value = "Base-Dict-Group")
public class BaseDictGroupController {

    @Autowired
    BaseDictGroupService baseDictGroupService;

    @ApiOperation(value = "添加数据字典分类-大后台", notes = "添加数据字典分类")
    @PostMapping(value = "create")
    public void create(@RequestBody @Valid BaseDictGroupCreateRequest baseDictGroupCreateRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        baseDictGroupService.create(baseDictGroupCreateRequest);
    }

    @ApiOperation(value = "编辑数据字典分类-大后台", notes = "编辑数据字典分类")
    @PostMapping(value = "edit")
    public void edit(@RequestBody @Valid BaseDictGroupEditRequest baseDictGroupEditRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        baseDictGroupService.edit(baseDictGroupEditRequest);
    }

    @ApiOperation(value = "删除数据字典分类-大后台", notes = "删除数据字典分类")
    @DeleteMapping(value = "delete/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        baseDictGroupService.delete(id);
    }

    @ApiOperation(value = "获取字典分类列表-大后台", notes = "用于分页显示已添加字典分类的数据")
    @PostMapping(value = "list")
    public PageDataResponse<BaseDictGroupPageResponse> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                            BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(PARAMS_ERROR);
        }
        return baseDictGroupService.searchPage(searchRequest);
    }

    @ApiOperation(value = "获取字典分类列表-大后台", notes = "获取城市树形列表")
    @GetMapping(value = "tree")
    public List<BaseDictGroupTreeResponse> tree() {
        return baseDictGroupService.tree();
    }

}
