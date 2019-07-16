package com.linkmoretech.versatile.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.linkmoretech.versatile.entity.UserAppVersion;
import com.linkmoretech.versatile.service.UserAppVersionService;
import com.linkmoretech.versatile.vo.request.UserAppVersionRequest;
import com.linkmoretech.versatile.vo.response.UserAppVersionPageResponse;
import com.linkmoretech.versatile.vo.response.UserAppVersionResponse;
import com.linkmoretech.versatile.vo.response.UserVersionRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 个人版版本管理
 * @author jhb
 * @Date 2019年6月27日 下午1:54:27
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "user-app-version")
@Api(tags = "个人版版本管理", value = "User-App-Version" )
public class UserAppVersionController {

    @Autowired
    UserAppVersionService userAppVersionService;
    
    @GetMapping(value="current")
	@ResponseBody
	@ApiOperation(value = "查询当前版本-APP", notes = "来源必填 1 android 2 ios")
	public UserAppVersionResponse current(@ApiParam(value="来源" ,required=true) @Length(min=1,max=2,message="来源在1-2之间") @NotNull(message="来源不能为空") @RequestParam("source")Integer source,HttpServletRequest request){
		UserAppVersion userAppVersion = this.userAppVersionService.currentAppVersion(source);
		UserAppVersionResponse userAppVersionResponse = new UserAppVersionResponse();
		userAppVersionResponse.setVersion(userAppVersion.getVersion());
		userAppVersionResponse.setVersionCode(userAppVersion.getCode());
		userAppVersionResponse.setDescription(userAppVersion.getDescription());
		userAppVersionResponse.setDownloadUrl(userAppVersion.getUrl());
		userAppVersionResponse.setMustUpdate(userAppVersion.getUpdateStatus());
		userAppVersionResponse.setVersionName(userAppVersion.getName());
		return userAppVersionResponse;
	}
	
	@PostMapping(value="report")
	@ApiOperation(value = "上报用户版本-APP", notes = "上报用户版本")
	public void report(Authentication authentication, @RequestBody @Validated UserVersionRequest uvr,HttpServletRequest request){
		AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
		this.userAppVersionService.report(uvr,authenticationTokenAnalysis.getUserId());
	}
	
	@PostMapping(value="save")
	@ApiOperation(value = "新增个人版版本-大后台", notes = "新增个人版版本")
	public void saveApp(@RequestBody UserAppVersionRequest version) {
		this.userAppVersionService.saveApp(version);
	}
	
	@PutMapping(value="update")
	@ApiOperation(value = "更新个人版版本-大后台", notes = "更新个人版版本")
	public void updateApp(@RequestBody UserAppVersionRequest version) {
		this.userAppVersionService.updateApp(version);
	}
	
	@DeleteMapping(value="delete")
	@ApiOperation(value = "删除个人版版本-大后台", notes = "删除个人版版本")
	public void deleteAppById(@RequestBody List<Long> ids) {
		this.userAppVersionService.deleteAppById(ids);
	}
	
	@ApiOperation(value = "获取列表-大后台", notes = "用于分页显示已添加的数据")
    @PostMapping(value = "list")
    public PageDataResponse<UserAppVersionPageResponse> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                                    BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return userAppVersionService.searchPage(searchRequest);
    }
}
