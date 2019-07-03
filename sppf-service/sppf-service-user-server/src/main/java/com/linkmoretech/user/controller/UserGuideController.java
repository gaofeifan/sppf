package com.linkmoretech.user.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.linkmoretech.user.service.UserGuideService;
import com.linkmoretech.user.vo.UserGuideResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户指引
 * @author jhb
 * @Date 2019年6月27日 下午1:54:27
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "user-guide")
@Api(tags = "用户指引", value = "UserGuide")
@Slf4j
public class UserGuideController {

    @Autowired
    UserGuideService userGuideService;
    
    @ApiOperation(value = "列表", notes = "列表", consumes = "application/json")
	@GetMapping(value = "list")
	public List<UserGuideResponse> list(HttpServletRequest request) {
		String language = request.getHeader("lan");
		List<UserGuideResponse> list = userGuideService.find(language);
		return list;
	}
}
