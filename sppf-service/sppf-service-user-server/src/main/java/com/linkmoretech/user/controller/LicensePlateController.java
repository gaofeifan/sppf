package com.linkmoretech.user.controller;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.user.service.LicensePlateService;
import com.linkmoretech.user.vo.LicensePlateAddRequest;
import com.linkmoretech.user.vo.LicensePlateResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 车牌号控制层
 * @Author: alec
 * @Description:
 * @date: 下午7:53 2019/4/10
 */
@RestController
@RequestMapping(value = "plate")
@Api(tags = "用户车牌号API", value = "LicensePlate" )
public class LicensePlateController {

    @Autowired
    LicensePlateService licensePlateService;

    @ApiOperation(value = "添加车牌号", notes = "添加用户车牌号")
    @PostMapping(value = "save")
    public void create(@RequestBody @Valid LicensePlateAddRequest licensePlateAddRequest, BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        licensePlateService.createPlate(licensePlateAddRequest);
    }

    @ApiOperation(value = "获取车牌号列表", notes = "根据用户UserId获取用户绑定车牌号")
    @GetMapping(value = "list")
    public List<LicensePlateResponse> list(@ApiParam("用户ID")  @RequestParam(value = "userId", required = true) String userId) {
        List<LicensePlateResponse> plateResponses = licensePlateService.findPlateByUserId(userId);
        return plateResponses;
    }

    @ApiOperation(value = "删除车牌号", notes = "根据用户ID和车牌号删除用户已绑定车牌号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "plateNo",value = "车牌号", dataType = "String", paramType = "query")
    })
    @DeleteMapping(value = "remove")
    public void remove(@RequestParam(value = "userId") String userId,
                       @RequestParam(value = "plateNo") String plateNo) throws CommonException {
        licensePlateService.removePlate(userId, plateNo);
    }
}
