package com.linkmoretech.user.client.fallback;

import org.springframework.stereotype.Component;
import com.linkmoretech.user.client.UserInfoClient;
import com.linkmoretech.user.common.vo.UserInfoInput;
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
public class UserInfoFallBackFactory implements FallbackFactory<UserInfoClient>{

	@Override
	public UserInfoClient create(Throwable arg0) {
		return new UserInfoClient() {
			@Override
	        public String createUser(UserInfoInput userInfoInput) {
	          log.error("user server is error request params {} ", userInfoInput);
	          return null;
	        }
        };
	}
}
