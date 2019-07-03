package com.linkmoretech.versatile.client.fallback;

import com.linkmoretech.versatile.client.SmsSendClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 11:14 2019-07-03
 */
@Slf4j
@Component
public class SmsSendFallBackFactory implements FallbackFactory<SmsSendClient> {

    @Override
    public SmsSendClient create(Throwable throwable) {
        return new SmsSendClient() {


            @Override
            public void sendNotifyMessage(String mobile, String code) {
                log.info("服务异常，熔断 {}", throwable.getMessage());
            }

            @Override
            public void sendValidateMessage(String mobile, String code, String value) {
                log.info("服务异常，熔断 {}", throwable.getMessage());
            }
        };
    }
}
