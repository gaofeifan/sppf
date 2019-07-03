package com.linkmoretech.user.controller;

import com.alibaba.fastjson.JSON;
import com.linkmoretech.auth.common.util.AuthenticationTokenAnalysis;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.user.common.vo.UserInfoInput;
import com.linkmoretech.user.enums.UserStatusEnum;
import com.linkmoretech.user.service.UserInfoService;
import com.linkmoretech.user.vo.UserEditRequest;
import com.linkmoretech.user.vo.UserInfoResponse;
import com.linkmoretech.user.vo.UserListResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * 用户信息控制器
 * @Author: alec
 * @Description:
 * @date: 下午5:00 2019/4/10
 */
@RestController
@RequestMapping(value = "user-info")
@Api(tags = "用户信息", value = "User" )
@Slf4j
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @ApiOperation(value = "获取用户列表", notes = "用于分页显示注册用户的数据")
    @PostMapping(value = "list")
    public PageDataResponse<UserListResponse> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                   BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        PageDataResponse<UserListResponse> dataResponse = userInfoService.searchList(searchRequest);
        return dataResponse;
    }

    @ApiOperation(value = "获取用户详情", notes = "根据用户ID查询用户详情")
    @GetMapping(value = "find")
    public UserInfoResponse detail(Authentication authentication) {
    	AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
        UserInfoResponse userInfoResponse = userInfoService.findDetailByUserId(authenticationTokenAnalysis.getUserId());
        return userInfoResponse;
    }

    @ApiOperation(value = "编辑用户信息", notes = "编辑用户的性别、车辆等信息")
    @PostMapping(value = "edit")
    public void edit(Authentication authentication, @RequestBody @Valid UserEditRequest userEditRequest, BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
        userEditRequest.setId(authenticationTokenAnalysis.getUserId());
        userInfoService.editUser(userEditRequest);
    }
    
    @ApiOperation(value = "创建用户信息", notes = "创建用户信息")
    @PostMapping(value = "create")
    public void create(Authentication authentication, @RequestBody @Valid UserInfoInput userInfoInput, BindingResult bindingResult) throws CommonException {
        log.info("create user param = {}",JSON.toJSON(userInfoInput));
    	if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
		AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
		userInfoInput.setUserId(authenticationTokenAnalysis.getUserId());
        userInfoService.createUser(userInfoInput);
    }

    @ApiOperation(value = "启用用户信息", notes = "启用用户信息")
    @PutMapping(value = "enable")
    public void enable(Authentication authentication, @RequestParam(value = "username") String username)
            throws CommonException {
		AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
        userInfoService.updateUserState(authenticationTokenAnalysis.getUserId(), username, UserStatusEnum.NORMAL.getCode());
    }

    @ApiOperation(value = "冻结用户信息", notes = "冻结用户信息")
    @PutMapping(value = "disabled")
    public void disabled(Authentication authentication, @RequestParam(value = "username") String username)
            throws CommonException {
		AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
        userInfoService.updateUserState(authenticationTokenAnalysis.getUserId(), username, UserStatusEnum.FREEZE.getCode());
    }
    
	@ApiOperation(value="解绑微信号",notes="解绑微信号")
	@GetMapping(value = "remove-wechat")
	public void removeWechat(Authentication authentication) throws CommonException { 
		AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
		this.userInfoService.unbindWeChat(authenticationTokenAnalysis.getUserId());
	}
	
	@ApiOperation(value="绑定微信号",notes="绑定微信号")
	@PutMapping(value = "bind-wechat")
	public void bindWechat(Authentication authentication, @RequestParam("code")String code) throws CommonException{ 
		AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
		this.userInfoService.bindWeChat(authenticationTokenAnalysis.getUserId(),code);
	}
    
}
