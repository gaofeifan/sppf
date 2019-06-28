package com.linkmoretech.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.linkmoretech.user.service.UserFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 投诉建议
 * @author jhb
 * @Date 2019年6月27日 下午1:54:27
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "user-feedback")
@Api(tags = "用户建议", value = "Feedback")
@Slf4j
public class UserFeedbackController {

    @Autowired
    UserFeedbackService userFeedbackService;
    
    @PostMapping(value = "save")
	@ResponseBody
	@ApiOperation(value="投诉建议保存",notes="内容不能为空", consumes = "application/json")
	public void save(
			@ApiParam(value = "内容", required = true) 
			@NotNull(message="内容不能为空") 
			@Length(min=3,max=300,message="内容长度需要在3-300之间")
			@RequestParam("content") String content,HttpServletRequest request) {
		String userId = "";
		log.info(content);
		this.userFeedbackService.save(content,userId);
	}

}
