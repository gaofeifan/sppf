package com.linkmoretech.user.controller;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.user.service.LeaseUserService;
import com.linkmoretech.user.vo.response.LeaseUserListResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: alec
 * Description:
 * @date: 17:23 2019-05-23
 */
@RestController
@RequestMapping(value = "lease")
public class LeaseUseController {

    @Autowired
    LeaseUserService leaseUserService;

    @ApiOperation(value = "获取用户列表", notes = "用于分页显示注册用户的数据")
    @PostMapping(value = "list")
    public PageDataResponse<LeaseUserListResponse> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                   BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        PageDataResponse<LeaseUserListResponse> dataResponse = leaseUserService.searchPage(searchRequest);
        return dataResponse;
    }
}
