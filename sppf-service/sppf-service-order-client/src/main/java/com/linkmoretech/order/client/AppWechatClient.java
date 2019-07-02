package com.linkmoretech.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.linkmoretech.auth.common.configuration.FeignConfiguration;
import com.linkmoretech.order.common.response.ResFans;

/**
 * 微信信息
 * @author jhb
 * @Date 2019年6月28日 上午11:13:52
 * @Version 1.0
 */
@FeignClient(name = "order" , configuration = FeignConfiguration.class)
public interface AppWechatClient {

    /**
	 * 根据code获取粉丝
	 * @param code 授权码
	 * @return
	 */
	@GetMapping(value = "app-wechat/fans")
	public ResFans getFans(@RequestParam(value = "code") String code);
    
}
