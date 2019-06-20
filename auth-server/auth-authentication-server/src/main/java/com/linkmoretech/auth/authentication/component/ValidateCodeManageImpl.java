package com.linkmoretech.auth.authentication.component;

import com.linkmoretech.auth.authentication.authentication.sms.mobile.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: alec
 * Description:
 * @date: 17:39 2019-06-19
 */
@Component
@Slf4j
public class ValidateCodeManageImpl implements ValidateCodeManage {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    private final String REDIS_VALIDATE_CODE = "v_code:";

    @Override
    public void saveValidateCode(ValidateCode validateCode, String clientId, String mobile) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = REDIS_VALIDATE_CODE + mobile + ":" + clientId;
        valueOperations.set(key, validateCode.getCode());
        redisTemplate.expire(key, validateCode.getExpire(), TimeUnit.SECONDS);
    }

    @Override
    public String findValidateCode(String clientId, String mobile) {
        log.info("clientId {}, mobile {}", clientId, mobile);
        String key = REDIS_VALIDATE_CODE + mobile + ":" + clientId;
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String code = valueOperations.get(key);
        return code;
    }

    @Override
    public void deleteValidateCode(String clientId, String mobile) {
        if(redisTemplate.hasKey(mobile)){
            redisTemplate.delete(mobile);
        }
    }
}
