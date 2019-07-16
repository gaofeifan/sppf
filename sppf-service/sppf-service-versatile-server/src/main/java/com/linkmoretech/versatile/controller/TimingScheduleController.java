package com.linkmoretech.versatile.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.entity.TimingSchedule;
import com.linkmoretech.versatile.service.TimingScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 定时任务
 * @author jhb
 * @Date 2019年6月27日 下午1:54:27
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "timing-schedule")
@Api(tags = "定时任务", value = "Timing-Schedule" )
public class TimingScheduleController {

    @Autowired
    TimingScheduleService timingScheduleService;
    
	@PostMapping(value="save")
	@ApiOperation(value = "新增定时任务-大后台", notes = "新增定时任务")
	public void save(@RequestBody TimingSchedule timingSchedule) {
		this.timingScheduleService.save(timingSchedule);
	}
	
	@PutMapping(value="update")
	@ApiOperation(value = "更新定时任务-大后台", notes = "更新定时任务")
	public void update(@RequestBody TimingSchedule timingSchedule) {
		this.timingScheduleService.update(timingSchedule);
	}
	
	@DeleteMapping(value="delete")
	@ApiOperation(value = "删除定时任务-大后台", notes = "删除定时任务")
	public void deleteById(@RequestBody List<Long> ids) {
		this.timingScheduleService.deleteById(ids);
	}
	
	@ApiOperation(value = "获取列表-大后台", notes = "用于分页显示已添加的数据")
    @PostMapping(value = "list")
    public PageDataResponse<TimingSchedule> list(@RequestBody @Valid PageSearchRequest searchRequest,
                                                                    BindingResult bindingResult) throws CommonException {
        if (bindingResult.hasErrors()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR);
        }
        return timingScheduleService.searchPage(searchRequest);
    }
}
