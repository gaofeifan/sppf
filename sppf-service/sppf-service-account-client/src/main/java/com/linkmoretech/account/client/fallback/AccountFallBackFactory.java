package com.linkmoretech.account.client.fallback;

import com.linkmoretech.account.client.AccountDataClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 11:07 2019-07-02
 */
@Component
@Slf4j
public class AccountFallBackFactory implements FallbackFactory<AccountDataClient> {

    @Override
    public AccountDataClient create(Throwable throwable) {
        return new AccountDataClient() {

            @Override
            public List<Long> getParkDataAccount(Long userId) {
                log.error("服务器熔断降级，返回空值 {}", throwable);
                return new ArrayList<>();
            }

            @Override
            public List<Long> getPlaceDataAccount(Long userId, Long parkId) {
                log.error("服务器熔断降级，返回空值 {}" , throwable);
                return new ArrayList<>();
            }
        };
    }
}
