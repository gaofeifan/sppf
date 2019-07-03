package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.entity.User;
import com.linkmoretech.account.enums.SmsTypeEnum;
import com.linkmoretech.account.resposity.UserRepository;
import com.linkmoretech.account.service.SmsCodeService;
import com.linkmoretech.auth.authentication.authentication.sms.mobile.ValidateCode;
import com.linkmoretech.auth.authentication.component.SmsValidateCodeGenerator;
import com.linkmoretech.auth.authentication.component.ValidateCodeManage;
import com.linkmoretech.auth.common.construct.ParamsConstruct;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.versatile.client.SmsSendClient;
import com.linkmoretech.versatile.common.enums.SmsTempEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: alec
 * Description: 用于短信码逻辑处理
 * @date: 17:14 2019-06-19
 */
@Service
@Slf4j
public class SmsCodeServiceImpl implements SmsCodeService {

   /**
    * 验证手机号是否存在
    * */

   @Autowired
    UserRepository userRepository;

   @Autowired
   SmsValidateCodeGenerator smsValidateCodeGenerator;

   @Autowired
   ValidateCodeManage validateCodeManage;


   @Autowired
   SmsSendClient smsSendClient;

   @Override
   public void createSmsCode(String mobile, String clientId, SmsTypeEnum smsTypeEnum) throws CommonException {

       if (ParamsConstruct.CLIENT_MANAGE.equals(clientId)) {
           /**
            * 验证用户是否存在
            * */
           validateUser(mobile, clientId);
       }
       /**
        * 生成随机验证码
        * */
       ValidateCode validateCode = smsValidateCodeGenerator.createValidateCode();
       /**
        * 存储验证码
        * */
       validateCodeManage.saveValidateCode(validateCode, clientId, smsTypeEnum.getCode(), mobile);
       log.info("向手机 {} 发送 登录 客户端{} 的验证码 {}", mobile, clientId, validateCode.getCode());

       /**
        * 调用短信服务， 发送短信验证码
        * */
       sendMessage(mobile, smsTypeEnum.getCode(), validateCode.getCode());
   }

    private void validateUser(String mobile, String clientId) throws CommonException {
       User user = userRepository.getUserByClientIdAndMobile(clientId, mobile);
       if (user == null) {
           throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "用户不存在");
       }
   }

   private void sendMessage(String mobile, Integer smsCode, String value) {
       String code = null;
       if (smsCode.equals(SmsTypeEnum.LOGIN.getCode())) {
           code = SmsTempEnum.LOGIN_VALIDATE.getCode();
       }
       if (smsCode.equals(SmsTypeEnum.REGISTER.getCode())) {
           code = SmsTempEnum.REGISTER_VALIDATE.getCode();
       }
       if (!StringUtils.isEmpty(code)) {
           smsSendClient.sendValidateMessage(mobile, SmsTempEnum.LOGIN_VALIDATE.getCode(), value);
       }
   }
}
