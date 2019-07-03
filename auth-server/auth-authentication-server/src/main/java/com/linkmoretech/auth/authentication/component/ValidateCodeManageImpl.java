package com.linkmoretech.auth.authentication.component;

import com.linkmoretech.auth.authentication.authentication.sms.mobile.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    public void saveValidateCode(ValidateCode validateCode, String clientId, Integer type, String mobile) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = REDIS_VALIDATE_CODE + mobile + ":" + type;
        if (!StringUtils.isEmpty(clientId)) {
            key = key + clientId;
        }
        valueOperations.set(key, validateCode.getCode());
        redisTemplate.expire(key, validateCode.getExpire(), TimeUnit.MINUTES);

    }

    @Override
    public void saveValidateCode(ValidateCode validateCode, Integer type, String mobile) {
        saveValidateCode(validateCode, null, type, mobile);
    }

    @Override
    public String findValidateCode(String clientId, Integer type, String mobile) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = REDIS_VALIDATE_CODE + mobile + ":" + type;
        if (!StringUtils.isEmpty(clientId)) {
            key = key + clientId;
        }
        log.info("redis key {}", key);
        String code = valueOperations.get(key);
        return code;
    }

    @Override
    public String findValidateCode(Integer type, String mobile) {
        return findValidateCode(null, type, mobile);
    }

    @Override
    public void deleteValidateCode(Integer type, String clientId, String mobile) {
        String key = REDIS_VALIDATE_CODE + mobile + ":" + type;
        if (!StringUtils.isEmpty(clientId)) {
            key = key + clientId;
        }
        if(redisTemplate.hasKey(key)){
            redisTemplate.delete(key);
        }
    }
}
