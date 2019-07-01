package com.linkmoretch.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.linkmoretech.order.common.response.ResFans;

/**
 * 微信信息
 * @author jhb
 * @Date 2019年6月28日 上午11:13:52
 * @Version 1.0
 */
@FeignClient(name = "app-wechat")
public interface AppWechatClient {

    /**
	 * 根据code获取粉丝
	 * @param code 授权码
	 * @return
	 */
	@GetMapping(value = "app-wechat/fans")
	@ResponseBody
	public ResFans getFans(@RequestParam(value = "code") String code);
    
}
