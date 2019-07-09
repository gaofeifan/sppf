package com.linkmoretech.parking.controller;

import com.linkmoretech.account.client.AccountDataClient;
import com.linkmoretech.auth.common.util.AuthenticationTokenAnalysis;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.service.CarParkService;
import com.linkmoretech.parking.vo.request.CarParkCreateRequest;
import com.linkmoretech.parking.vo.request.CarParkEditRequest;
import com.linkmoretech.parking.vo.request.CarParkLineRequest;
import com.linkmoretech.parking.vo.response.CarParkEditResponse;
import com.linkmoretech.parking.vo.response.CarParkInfoResponse;
import com.linkmoretech.parking.vo.response.CarParkPageResponse;
import com.linkmoretech.parking.vo.response.CarParkSelectResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: alec
 * @Description:
 * @date: 9:58 AM 2019/4/19
 */
@RestController
@RequestMapping(value = "car/park")
@Slf4j
@Api(tags = "车场操作API", value = "CarParkController" )
public class CarParkController {

    @Autowired
    CarParkService carParkService;

    @ApiOperation(value = "添加车场", notes = "添加车场")
    @PostMapping(value = "create")
    public void create(Authentication authentication , @RequestBody @Valid CarParkCreateRequest carParkCreateRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
        carParkCreateRequest.setUsername(authenticationTokenAnalysis.getUsername());
        carParkService.create(carParkCreateRequest);
    }

    @ApiOperation(value = "获取车场列表", notes = "用于分页显示已添加车场的数据")
    @PostMapping(value = "list")
    public PageDataResponse<CarParkPageResponse> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                      BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return carParkService.searchPage(searchRequest);
    }

    @ApiOperation(value = "获取车场详情", notes = "根据车场ID查询车场详情")
    @GetMapping(value = "find")
    public CarParkInfoResponse detail(@ApiParam("车场ID") @RequestParam(value = "id") Long id) throws CommonException {
       return carParkService.findDetail(id);
    }

    @ApiOperation(value = "删除车场", notes = "根据车场ID删除车场信息")
    @DeleteMapping(value = "remove")
    public void remove(@RequestParam(value = "id") Long id) throws CommonException {
        carParkService.remove(id);
    }

    @ApiOperation(value = "车场下拉数据", notes = "根据城市查询车场")
    @GetMapping(value = "enable-list")
    public List<CarParkSelectResponse> listByCityCode(@ApiParam("城市code") @RequestParam(value = "cityCode") String cityCode) {
        return carParkService.getSelectedData(cityCode);
    }

    @ApiOperation(value = "获取车场编辑详情", notes = "根据车场ID查询车场编辑详情")
    @GetMapping(value = "detail")
    public CarParkEditResponse detailForEdit(@ApiParam("车场ID") @RequestParam(value = "id") Long id) throws CommonException {
        return carParkService.findEditInfo(id);
    }

    @ApiOperation(value = "编辑车场", notes = "编辑车场")
    @PostMapping(value = "edit")
    public void edit(Authentication authentication , @RequestBody @Valid CarParkEditRequest carParkEditRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
        carParkEditRequest.setUsername(authenticationTokenAnalysis.getUsername());
        carParkService.edit(carParkEditRequest);
    }

    @ApiOperation(value = "更新车位上下线", notes = "更新车位上下线")
    @PostMapping(value = "line")
    public void upDownLine(@RequestBody @Valid CarParkLineRequest carParkLineRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        carParkService.upDownLine(carParkLineRequest);
    }
}
