package com.linkmoretech.gateway.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DefaultHystrixController {

	@RequestMapping("/default-fallback")
	public String defaultFallback() {
		log.error("转发服务熔断");
		return "hystrix";
	}

}
