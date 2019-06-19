package com.linkmoretech.parking.controller;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.service.CarPlaceService;
import com.linkmoretech.parking.vo.request.CarPlaceCreateRequest;
import com.linkmoretech.parking.vo.request.CarPlaceEditRequest;
import com.linkmoretech.parking.vo.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: alec
 * Description: 车位控制层
 * @date: 11:14 2019-05-08
 */
@RestController
@RequestMapping("car/place")
@Slf4j
@Api(tags = "车位操作API", value = "CarPlaceController" )
public class CarPlaceController {

    @Autowired
    CarPlaceService carPlaceService;

    @ApiOperation(value = "添加车位", notes = "添加车位")
    @PostMapping(value = "create")
    public void create(@RequestBody CarPlaceCreateRequest carPlaceCreateRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        carPlaceService.create(carPlaceCreateRequest);
    }

    @ApiOperation(value = "获取车位列表", notes = "用于分页显示已添加车位的数据")
    @PostMapping(value = "list")
    public PageDataResponse<CarPlacePageResponse> list(@RequestBody PageSearchRequest pageSearchRequest ,
                                                       BindingResult bindingResult) throws CommonException{
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        return carPlaceService.searchPage(pageSearchRequest);
    }


    @ApiOperation(value = "车位下拉数据", notes = "根据车场查询可用车位")
    @GetMapping(value = "enable-list")
    public List<CarPlaceSelectedResponse> listByCityCode(@ApiParam("车场ID") @RequestParam(value = "id") Long id) {
        return carPlaceService.getSelectedData(id);
    }

    @ApiOperation(value = "删除车位", notes = "根据车位ID删除车位信息")
    @DeleteMapping(value = "remove")
    public void remove(@RequestParam(value = "id") Long id) throws CommonException {
        carPlaceService.remove(id);
    }

    @ApiOperation(value = "获取车位详情", notes = "根据车位ID查询车位详情")
    @GetMapping(value = "find")
    public CarPlaceInfoResponse detail(@ApiParam("车位ID") @RequestParam(value = "id") Long id) throws CommonException {
        return carPlaceService.findDetail(id);
    }

    @ApiOperation(value = "获取车位编辑详情", notes = "根据车位ID查询车位编辑详情")
    @GetMapping(value = "detail")
    public CarPlaceEditResponse detailForEdit(@ApiParam("车场ID") @RequestParam(value = "id") Long id) throws CommonException {
        return carPlaceService.findEditInfo(id);
    }

    @ApiOperation(value = "编辑车位", notes = "编辑车位")
    @PostMapping(value = "edit")
    public void edit(@RequestBody @Valid CarPlaceEditRequest carPlaceEditRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        carPlaceService.edit(carPlaceEditRequest);
    }

    @ApiOperation(value = "更新上线下线状态", notes = "更新上线下线状态")
    @PutMapping(value = "update-line")
    public void updateLine(@RequestParam(value = "id") Long id, @RequestParam(value = "lineStatus") Integer lineStatus)
            throws CommonException {
        carPlaceService.upDownLine(id, lineStatus);
    }
}
