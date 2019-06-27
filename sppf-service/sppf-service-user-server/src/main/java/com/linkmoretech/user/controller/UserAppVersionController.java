package com.linkmoretech.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.linkmoretech.user.entity.UserAppVersion;
import com.linkmoretech.user.service.UserAppVersionService;
import com.linkmoretech.user.vo.UserAppVersionResponse;
import com.linkmoretech.user.vo.UserVersionRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户版本
 * @author jhb
 * @Date 2019年6月27日 下午1:54:27
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "user-app-version")
@Api(tags = "用户版本", value = "App-Version" )
public class UserAppVersionController {

    @Autowired
    UserAppVersionService userAppVersionService;
    
    /**
     * 查询当前版本
     * @param source
     * @param request
     * @return
     */
    @GetMapping(value="current")
	@ResponseBody
	@ApiOperation(value = "查询当前版本", notes = "来源必填 1 android 2 ios", consumes = "application/json")
	public UserAppVersionResponse current(@ApiParam(value="来源" ,required=true) @NotNull(message="来源不能为空") @RequestParam("source")Integer source,HttpServletRequest request){
		UserAppVersion userAppVersion = this.userAppVersionService.currentAppVersion(source);
		UserAppVersionResponse userAppVersionResponse = new UserAppVersionResponse();
		BeanUtils.copyProperties(userAppVersion, userAppVersionResponse);
		return userAppVersionResponse;
	}
	
    /**
     * 上报用户版本
     * @param uvr
     * @param request
     */
	@PostMapping(value="report")
	@ApiOperation(value = "上报用户版本", notes = "上报用户版本", consumes = "application/json")
	public void report(@RequestBody @Validated UserVersionRequest uvr,HttpServletRequest request){
		String userId = "";
		this.userAppVersionService.report(uvr,userId);
	}

}
