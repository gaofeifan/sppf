package com.linkmoretech.order.client.fallback;

import org.springframework.stereotype.Component;

import com.linkmoretech.order.client.AppWechatClient;
import com.linkmoretech.order.common.response.ResFans;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
/**
 * 用户服务熔断
 * @author jhb
 * @Date 2019年7月2日 下午5:00:48
 * @Version 1.0
 */
@Component
@Slf4j
public class AppWechatFallBackFactory implements FallbackFactory<AppWechatClient>{@Override
	public AppWechatClient create(Throwable arg0) {
	
		return new AppWechatClient() {
			@Override
			public ResFans getFans(String code) {
		          log.error("order server is error request params {} ", code);
				return null;
			}
			
		};
	}
}
