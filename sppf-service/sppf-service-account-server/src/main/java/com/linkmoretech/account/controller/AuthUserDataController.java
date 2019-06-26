package com.linkmoretech.account.controller;

import com.linkmoretech.account.service.UserDataAuthService;
import com.linkmoretech.account.vo.request.AuthParkAddRequest;
import com.linkmoretech.account.vo.request.AuthPlaceAddRequest;
import com.linkmoretech.account.vo.response.AuthDataListResponse;
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

    /**
     * 添加车场权限
     * */
    @PostMapping(value = "add-place")
    public void addPlace(@RequestBody AuthPlaceAddRequest authPlaceAddRequest, BindingResult bindingResult)
            throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        userDataAuthService.addPlaceAuth(authPlaceAddRequest);
    }




    @PostMapping(value = "list")
    public PageDataResponse<AuthDataListResponse> list(@RequestBody PageSearchRequest<Long> pageRequest,
                                                       BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return userDataAuthService.list(pageRequest);
    }

    @DeleteMapping(value = "remove-park")
    public void removeParkAuth(@RequestParam(value = "authId") Long authId) throws CommonException {
        userDataAuthService.removeParkAuth(authId);
    }

    @DeleteMapping(value = "clean-place")
    public void cleanPlace(@RequestParam(value = "authId") Long authId) throws CommonException {
        userDataAuthService.cleanPlaceAuth(authId);
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
    public List<Long> getPlaceDataAccount(@RequestParam(value = "userId") Long userId, @RequestParam(value = "parkId")
            Long parkId) {
        return userDataAuthService.getPlaceNoList(userId, parkId);
    }

}
