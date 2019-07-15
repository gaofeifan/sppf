package com.linkmoretech.station.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.station.enums.CameraStatusEnum;
import com.linkmoretech.station.service.CameraService;
import com.linkmoretech.station.vo.request.CameraRequest;
import com.linkmoretech.station.vo.response.CameraPageResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备管理
 * @author jhb
 * @Date 2019年6月27日 下午1:54:27
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "camera")
@Api(tags = "摄像头", value = "Camera")
@Slf4j
public class CameraController {

    @Autowired
    CameraService cameraService;
    
    @PostMapping(value="save")
	@ApiOperation(value = "添加设备", notes = "添加设备")
	public void save(@RequestBody @Valid CameraRequest camera, BindingResult bindingResult) throws CommonException{
    	if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
    	this.cameraService.save(camera);
	}
	
	@PutMapping(value="update")
	@ApiOperation(value = "更新设备", notes = "更新设备")
	public void update(@RequestBody @Valid CameraRequest camera, BindingResult bindingResult) throws CommonException{
		if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
		this.cameraService.update(camera);
	}
	
	@DeleteMapping(value="delete")
	@ApiOperation(value = "删除设备", notes = "删除设备")
	public void delete(@RequestParam("id") Long id) {
		this.cameraService.delete(id);
	}
	
	@ApiOperation(value = "获取设备详情", notes = "根据ID获取设备详情")
    @GetMapping(value = "detail")
    public CameraPageResponse detailForEdit(@ApiParam("主键ID") @RequestParam(value = "id") Long id) throws CommonException {
        return cameraService.findById(id);
    }
	
	@ApiOperation(value = "启用", notes = "启用")
    @GetMapping(value = "enable")
    public void enable(@RequestParam(value = "id") Long id)
            throws CommonException {
		cameraService.updateEnableStatus(id, CameraStatusEnum.ENABLED.getCode());
    }

    @ApiOperation(value = "禁用", notes = "禁用")
    @GetMapping(value = "disable")
    public void disable(@RequestParam(value = "id") Long id)
            throws CommonException {
    	cameraService.updateEnableStatus(id, CameraStatusEnum.DISABLED.getCode());
    }
	
	@ApiOperation(value = "获取列表", notes = "用于分页显示已添加的数据")
    @PostMapping(value = "list")
    public PageDataResponse<CameraPageResponse> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                                    BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return cameraService.searchPage(searchRequest);
    }

}
