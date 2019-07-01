package com.linkmoretech.auth.common.construct;

/**
 * @Author: alec
 * Description:
 * @date: 20:36 2019-06-12
 */
public interface ParamsConstruct {

   /**
    * 大后台登录
    * */
   String LOGIN_CUSTOMER = "/system/login";

   /**
    * 短信验证码发送
    * */
   String SEND_SMS = "/sms/code";
   /**
    * 管理版手机号登录
    * */
   String LOGIN_MANAGE_MOBILE = "/login-mobile";


   /**
    * 个人版APP注册
    * */
   String REGISTER_APP = "/app/register";

   /**
    * 个人版登录
    * */
   String LOGIN_PERSION = "/app/login";

   /**
    * json登录不需要跳转至登录界面，需要提示未登录信息，默认提示地址
    * */
   String NO_LOGIN_TIP_INFO = "/no-login";

   String LOGIN_MOBILE_PERSONAL = "/personal/login-mobile";

   String SWAGGER_URL = "/swagger-ui.html";

   String CSS = "/swagger-resources/**";

   String JS = "/webjars/**";

   String DOC = "/v2/**";

   /**
    * 管理版客户端标示
    * */
   String CLIENT_MANAGE = "manage";


   String CLIENT_ID = "clientId";

   String MOBILE_PARAMS = "mobile";

   String SOURCE = "type";
}
