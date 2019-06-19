package com.linkmoretech.user.controller;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.user.enums.UserStatusEnum;
import com.linkmoretech.user.service.UserInfoService;
import com.linkmoretech.user.vo.UserEditRequest;
import com.linkmoretech.user.vo.UserInfoResponse;
import com.linkmoretech.user.vo.UserListResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "user")
@Api(tags = "用户API", value = "User" )
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
    public UserInfoResponse detail(@ApiParam("用户ID")  @RequestParam(value = "userId", required = true) String userId) {
        UserInfoResponse userInfoResponse = userInfoService.findDetailByUserId(userId);
        return userInfoResponse;
    }

    @ApiOperation(value = "编辑用户信息", notes = "编辑用户的性别、车辆等信息")
    @PostMapping(value = "edit")
    public void edit(@RequestBody @Valid UserEditRequest userEditRequest, BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        userInfoService.editUser(userEditRequest);
    }

    @PutMapping(value = "enable")
    public void enable(@RequestParam(value = "userId") String userId, @RequestParam(value = "username") String username)
            throws CommonException {
        userInfoService.updateUserState(userId, username, UserStatusEnum.NORMAL.getCode());
    }

    @PutMapping(value = "disabled")
    public void disabled(@RequestParam(value = "userId") String userId, @RequestParam(value = "username") String username)
            throws CommonException {
        userInfoService.updateUserState(userId, username, UserStatusEnum.FREEZE.getCode());
    }
}
