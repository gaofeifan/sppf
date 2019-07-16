package com.linkmoretech.versatile.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.service.UserVersionService;
import com.linkmoretech.versatile.vo.response.UserVersionPageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户版本
 * @author jhb
 * @Date 2019年6月27日 下午1:54:27
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "user-version")
@Api(tags = "用户版本", value = "User-Version" )
public class UserVersionController {

    @Autowired
    UserVersionService userVersionService;
	
	@ApiOperation(value = "获取列表-大后台", notes = "用于分页显示已添加的数据")
    @PostMapping(value = "list")
    public PageDataResponse<UserVersionPageResponse> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                                    BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return userVersionService.searchPage(searchRequest);
    }
}
