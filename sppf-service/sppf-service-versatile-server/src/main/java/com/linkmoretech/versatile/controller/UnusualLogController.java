package com.linkmoretech.versatile.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.service.UnusualLogService;
import com.linkmoretech.versatile.vo.request.UnusualLogRequest;
import com.linkmoretech.versatile.vo.response.UnusualLogPageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 异常日志上报
 * @author jhb
 * @Date 2019年7月11日 下午3:52:14
 * @Version 1.0
 */
@RestController(value = "UnusualLogController")
@RequestMapping("unusual-log")
@Api(tags = "异常日志上报", value = "Unusual-Log")
public class UnusualLogController {

    @Autowired
    UnusualLogService unusualLogService;

    @PostMapping(value = "upload")
	@ResponseBody
	@ApiOperation(value="新增异常日志上报-APP",notes="新增异常日志上报")
	public void upload(@RequestBody @Validated UnusualLogRequest unusualLogRequest) {
		this.unusualLogService.save(unusualLogRequest);
	}
    
    @ApiOperation(value = "获取异常日志列表-大后台", notes = "用于分页显示已添加的数据")
    @PostMapping(value = "list")
    public PageDataResponse<UnusualLogPageResponse> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                                    BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return unusualLogService.searchPage(searchRequest);
    }
}
