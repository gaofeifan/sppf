package com.linkmoretech.account.controller;

/**
 * @Author: alec
 * Description:
 * @date: 11:14 2019-06-04
 */

import com.linkmoretech.account.enums.LoginTypeEnum;
import com.linkmoretech.account.service.AccountService;
import com.linkmoretech.account.service.SmsCodeService;
import com.linkmoretech.account.vo.request.PasswordEditRequest;
import com.linkmoretech.auth.common.bean.AccountUserDetail;
import com.linkmoretech.auth.common.util.AuthenticationTokenAnalysis;
import com.linkmoretech.common.annation.IgnoreResponseAdvice;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


/**
* 账户登入，登出
* */
@RestController
@Slf4j
@Api(tags = "账户服务-授权服务器", value = "AccountController" )
public class AccountController {

    @Autowired
    ConsumerTokenServices consumerTokenServices;

    @Autowired
    SmsCodeService smsCodeService;

    @Autowired
    AccountService accountService;

    @GetMapping(value = "principal")
    @IgnoreResponseAdvice
    public Principal user(Principal user) {
        log.info("user {}", user.getClass().getName());
        return user;
    }

    /***/
    @GetMapping(value = "revoke")
    @ApiOperation(value="注销用户登录", notes="注销当前登录用户Token",produces = "application/json")
    @ApiImplicitParam(name = "accessToken",value = "当前用户Token值",dataType = "String",paramType = "String")
    public void revokeToken(@RequestParam(value = "accessToken") String accessToken) {
        consumerTokenServices.revokeToken(accessToken);
    }

    @GetMapping(value = "authentication/user")
    public UserDetails userDetails (AccountUserDetail userDetails) {
        return userDetails;
    }


    @PostMapping(value = "modify")
    @ApiOperation(value="修改用户秘密", notes="修改当前用户登录密码,修改成功后需要客户端发起注销",produces = "application/json")
    public void updateUserPassword(Authentication authentication,
                                   @RequestBody PasswordEditRequest passwordEditRequest,
                                   BindingResult bindingResult)
            throws CommonException {

        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
        String username = authenticationTokenAnalysis.getUsername();
        String clientId = authenticationTokenAnalysis.getClientId();
        if (passwordEditRequest.getType().equals(LoginTypeEnum.MOBILE.getCode())) {
            accountService.updatePassword(clientId, username, passwordEditRequest.getPassword());
        }
        if (passwordEditRequest.getType().equals(LoginTypeEnum.ACCOUNT.getCode())) {
            accountService.updatePassword(clientId, username, passwordEditRequest.getPassword(),
                    passwordEditRequest.getOldPassword());
        }
    }

    /**
     * 生成短信验证码
     * */
    @GetMapping(value = "sms/code")
    @ApiOperation(value="发送登录验证码", notes="手机号登录时发送认证验证码",produces = "application/json")
    public void createSmsCode(@RequestParam (value = "clientId") String clientId,
                              @RequestParam (value = "mobile") String mobile) throws CommonException {
        smsCodeService.createSmsCode(mobile, clientId);
    }
}
