package com.linkmoretech.account.controller;

import com.linkmoretech.account.service.UserService;
import com.linkmoretech.account.vo.request.SearchRequest;
import com.linkmoretech.account.vo.request.UserCreateRequest;
import com.linkmoretech.account.vo.response.UserInfoResponse;
import com.linkmoretech.account.vo.response.UserListResponse;
import com.linkmoretech.auth.common.util.AuthenticationTokenAnalysis;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: alec
 * Description:
 * @date: 17:05 2019-05-31
 */
@RestController(value = "UserController")
@RequestMapping(value = "user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "list")
    public PageDataResponse<UserListResponse> list(@RequestBody PageSearchRequest<SearchRequest> searchRequest,
                                                   BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return userService.search(searchRequest);
    }

    @PostMapping(value = "create")
    public void create(@RequestBody UserCreateRequest userCreateRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        userService.createUser(userCreateRequest);
    }

    @GetMapping(value = "info")
    public UserInfoResponse info(Authentication authentication) {
        AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
        String username = authenticationTokenAnalysis.getUsername();
        String clientId = authenticationTokenAnalysis.getClientId();
        return userService.getUserInfo(username,clientId);
    }

    @GetMapping(value = "enable")
    public void enable(@RequestParam(value = "userId") Long userId, @RequestParam(value = "status") Integer status)
            throws CommonException {
        userService.updateUserState(userId, status);
    }
}
