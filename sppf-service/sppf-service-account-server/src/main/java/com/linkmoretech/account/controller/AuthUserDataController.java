package com.linkmoretech.account.controller;

import com.linkmore.account.common.request.AccountParkRequest;
import com.linkmore.account.common.request.AccountPlaceRequest;
import com.linkmoretech.account.service.UserDataAuthService;
import com.linkmoretech.account.vo.request.AuthParkAddRequest;
import com.linkmoretech.account.vo.request.SearchRequest;
import com.linkmoretech.account.vo.request.UserCreateRequest;
import com.linkmoretech.account.vo.response.AuthDataListResponse;
import com.linkmoretech.account.vo.response.ResourcesListResponse;
import com.linkmoretech.common.annation.IgnoreResponseAdvice;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: alec
 * Description: 用户数据权限
 * @date: 17:09 2019-06-24
 */
@RestController
@RequestMapping(value = "auth-user")
@Slf4j
public class AuthUserDataController {

    @Autowired
    UserDataAuthService userDataAuthService;

    /**
     * 添加车场权限
     * */
    @PostMapping(value = "add-park")
    public void addPark(@RequestBody AuthParkAddRequest authParkAddRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        userDataAuthService.addParkAuth(authParkAddRequest);
    }

    @PostMapping(value = "list")
    public PageDataResponse<AuthDataListResponse> list(@RequestBody PageSearchRequest<Long> pageRequest,
                                                       BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return userDataAuthService.list(pageRequest);
    }



    /**
     * 供其他服务调用数据权限
     * */

    @GetMapping(value = "getParkData")
    @IgnoreResponseAdvice
    public List<Long> getParkDataAccount(@RequestParam(value = "userId") Long userId){

        return userDataAuthService.getParkIdList(userId);
    }


    @GetMapping(value = "getPlaceData")
    @IgnoreResponseAdvice
    public List<String> getPlaceDataAccount(@RequestParam(value = "userId") Long userId, @RequestParam(value = "parkId")
            Long parkId) {
        return userDataAuthService.getPlaceNoList(userId, parkId);
    }

}
