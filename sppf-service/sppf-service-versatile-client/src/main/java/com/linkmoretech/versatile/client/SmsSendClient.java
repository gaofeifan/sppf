package com.linkmoretech.versatile.client;

import com.linkmoretech.auth.common.configuration.FeignConfiguration;
import com.linkmoretech.versatile.client.fallback.SmsSendFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: alec
 * Description: 短信发送验证码
 * @date: 11:13 2019-07-03
 */
@FeignClient(name = "versatile", fallbackFactory = SmsSendFallBackFactory.class, configuration = FeignConfiguration.class)
public interface SmsSendClient {

    /**
     * 发送通知类验证码
     * */
    @GetMapping(value = "send/notify")
    void sendNotifyMessage(@RequestParam(value = "mobile") String mobile,
                                  @RequestParam (value = "code") String code);
    /**
     * 发送验证码
     * */
    @GetMapping(value = "send/validate")
    void sendValidateMessage(@RequestParam (value = "mobile") String mobile,
                                    @RequestParam (value = "code") String code,
                                    @RequestParam (value = "value") String value);
}
