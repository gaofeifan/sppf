package com.linkmoretech.versatile.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.linkmoretech.auth.common.util.AuthenticationTokenAnalysis;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.service.StaffAppVersionService;
import com.linkmoretech.versatile.vo.request.StaffAppVersionCreateRequest;
import com.linkmoretech.versatile.vo.request.StaffAppVersionEditRequest;
import com.linkmoretech.versatile.vo.request.StaffAppVersionRequest;
import com.linkmoretech.versatile.vo.response.StaffAppVersionPageResponse;
import com.linkmoretech.versatile.vo.response.StaffAppVersionResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 管理版版本管理
 * @Author: jhb
 * @Description:
 * @date: 10:27 AM 2019/4/30
 */
@RestController
@RequestMapping("staff-app-version")
@Api(tags = "管理版版本管理", value = "Staff-App-Version")
public class StaffAppVersionController {

    @Autowired
    StaffAppVersionService staffAppVersionService;


    @ApiOperation(value = "添加版本管理-大后台", notes = "添加版本管理")
    @PostMapping(value = "create")
    public void create(@RequestBody @Valid StaffAppVersionCreateRequest staffAppVersionCreateRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        staffAppVersionService.create(staffAppVersionCreateRequest);
    }

    @ApiOperation(value = "编辑版本管理-大后台", notes = "编辑版本管理")
    @PostMapping(value = "edit")
    public void edit(@RequestBody @Valid StaffAppVersionEditRequest staffAppVersionEditRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        staffAppVersionService.edit(staffAppVersionEditRequest);
    }

    @ApiOperation(value = "删除版本管理-大后台", notes = "删除版本管理")
    @DeleteMapping(value = "delete/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        staffAppVersionService.delete(id);
    }

    @ApiOperation(value = "获取列表-大后台", notes = "用于分页显示已添加的数据")
    @PostMapping(value = "list")
    public PageDataResponse<StaffAppVersionPageResponse> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                                    BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return staffAppVersionService.searchPage(searchRequest);
    }
    
	@GetMapping(value="/current")
	@ResponseBody
	@ApiOperation(value = "查询当前版本-APP", notes = "来源必填 1 android 2 ios")
	public StaffAppVersionResponse current(@RequestParam("source")@ApiParam(value="来源 1 android 2 ios",required=true) @NotNull(message="参数不能为空") Integer source){
		int appType = 0;
		if(1 == source){
			appType = 1;
		}else if(2 == source){
			appType = 2;
		}
		StaffAppVersionResponse staffAppVersionResponse = this.staffAppVersionService.currentAppVersion(appType);
		return staffAppVersionResponse;
	}
    
	@PostMapping(value="/report")
	@ApiOperation(value = "上报用户版本-APP", notes = "上报用户版本")
	public void report(Authentication authentication, @RequestBody @Validated StaffAppVersionRequest staffAppVersionRequest){
		AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
		Long userId = authenticationTokenAnalysis.getUserId();
		this.staffAppVersionService.report(staffAppVersionRequest,userId);
	}
}
