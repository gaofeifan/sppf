package com.linkmoretech.parking.controller;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.enums.UserStatusEnum;
import com.linkmoretech.parking.service.LeasePlaceService;
import com.linkmoretech.parking.vo.request.LeasePlaceBatchRequest;
import com.linkmoretech.parking.vo.request.LeasePlaceCreateRequest;
import com.linkmoretech.parking.vo.response.LeasePlaceEditResponse;
import com.linkmoretech.parking.vo.response.LeasePlaceInfoResponse;
import com.linkmoretech.parking.vo.response.LeasePlaceListResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
/**
 * @Author: alec
 * Description: 长租车位控制层
 * @date: 18:31 2019-05-08
 */
@RestController
@RequestMapping(value = "lease/place")
@Slf4j
@Api(tags = "长租车位操作API", value = "LeasePlaceController" )
public class LeasePlaceController {

    @Autowired
    LeasePlaceService leasePlaceService;

    @ApiOperation(value = "获取车位列表", notes = "用于分页显示已添加车位的数据")
    @PostMapping(value = "list")
    public PageDataResponse<LeasePlaceListResponse> list(@RequestBody PageSearchRequest pageSearchRequest ,
                                                         BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        return leasePlaceService.searchPage(pageSearchRequest);
    }

    @ApiOperation(value = "添加长租车位", notes = "添加长租车位")
    @PostMapping(value = "create")
    public void create(@RequestBody @Valid LeasePlaceCreateRequest leasePlaceCreateRequest,
                       BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        leasePlaceService.create(leasePlaceCreateRequest);
    }

    @ApiOperation(value = "批量添加长租车位", notes = "批量添加长租车位")
    @PostMapping(value = "import")
    public void batchSave(@RequestBody @Valid LeasePlaceBatchRequest leasePlaceBatchRequest,
                       BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        leasePlaceService.batchImport(leasePlaceBatchRequest);
    }

    @ApiOperation(value = "删除长租车位", notes = "删除长租车位信息")
    @DeleteMapping(value = "remove")
    public void remove(@RequestParam(value = "id") Long id, @RequestParam(value = "username") String username)
            throws CommonException {
        leasePlaceService.removeId(id, username);
    }

    @ApiOperation(value = "启用长租车位", notes = "启用长租车位")
    @GetMapping(value = "enable")
    public void enable(@RequestParam(value = "id") Long id, @RequestParam(value = "username") String username)
            throws CommonException {
        leasePlaceService.updateEnableStatus(id, UserStatusEnum.ENABLED.getCode(), username);
    }

    @ApiOperation(value = "禁用长租车位", notes = "禁用长租车位")
    @GetMapping(value = "disable")
    public void disable(@RequestParam(value = "id") Long id, @RequestParam(value = "username") String username)
            throws CommonException {
        leasePlaceService.updateEnableStatus(id, UserStatusEnum.DISABLED.getCode(), username);
    }

    @ApiOperation(value = "查询明细", notes = "查询明细")
    @GetMapping(value = "info")
    public LeasePlaceInfoResponse info(@RequestParam(value = "id") Long id) throws CommonException {
        return leasePlaceService.getLeasePlaceDetail(id);
    }

    @ApiOperation(value = "查询编辑信息", notes = "查询编辑信息")
    @GetMapping(value = "detail")
    public LeasePlaceEditResponse detail(@RequestParam(value = "leaseCode") String leaseCode) throws CommonException {
        return leasePlaceService.getDetail(leaseCode);
    }

}
