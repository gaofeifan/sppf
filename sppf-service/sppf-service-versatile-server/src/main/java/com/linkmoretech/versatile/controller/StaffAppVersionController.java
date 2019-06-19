package com.linkmoretech.versatile.controller;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.service.StaffAppVersionService;
import com.linkmoretech.versatile.vo.request.StaffAppVersionCreateRequest;
import com.linkmoretech.versatile.vo.request.StaffAppVersionEditRequest;
import com.linkmoretech.versatile.vo.response.StaffAppVersionPageResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 版本管理controller
 * @Author: jhb
 * @Description:
 * @date: 10:27 AM 2019/4/30
 */
@RestController(value = "staffAppVersion")
@RequestMapping("staff/app/version")
public class StaffAppVersionController {

    @Autowired
    StaffAppVersionService staffAppVersionService;


    @ApiOperation(value = "添加版本管理", notes = "添加版本管理")
    @PostMapping(value = "create")
    public void create(@RequestBody @Valid StaffAppVersionCreateRequest staffAppVersionCreateRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        staffAppVersionService.create(staffAppVersionCreateRequest);
    }

    @ApiOperation(value = "编辑版本管理", notes = "编辑版本管理")
    @PostMapping(value = "edit")
    public void edit(@RequestBody @Valid StaffAppVersionEditRequest staffAppVersionEditRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        staffAppVersionService.edit(staffAppVersionEditRequest);
    }

    @ApiOperation(value = "删除版本管理", notes = "删除版本管理")
    @DeleteMapping(value = "delete/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        staffAppVersionService.delete(id);
    }

    @ApiOperation(value = "获取列表", notes = "用于分页显示已添加的数据")
    @PostMapping(value = "list")
    public PageDataResponse<StaffAppVersionPageResponse> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                                    BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return staffAppVersionService.searchPage(searchRequest);
    }


}
