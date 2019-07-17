package com.linkmoretech.auth.common.construct;

/**
 * @Author: alec
 * Description:
 * @date: 20:36 2019-06-12
 */
public interface ParamsConstruct {

   /**
    * 后台登录
    * */
   String LOGIN_CUSTOMER = "/system/login";

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
    * 个人版小程序
    * */
   String LOGIN_PERSON_CODE = "/app/login-code";

   /**
    * json登录不需要跳转至登录界面，需要提示未登录信息，默认提示地址
    * */

   String LOGIN_MOBILE_PERSONAL = "/personal/login-mobile";

   String APP_REGISTER = "/register-mobile";
   /**
    * 管理版客户端标示
    * */
   String CLIENT_MANAGE = "manage";

   String CLIENT_APP = "personal";

   String CLIENT_ID = "clientId";

   String MOBILE_PARAMS = "mobile";



   String SOURCE = "type";

   String CODE = "code";

   /**
    * 验证码类型 1 登录
    * */
   Integer SMS_TYPE_LOGIN = 0;

   /**
    * 验证码类型 2 注册
    * */
   Integer SMS_TYPE_REGISTER = 1;

   /**
    * 验证码类型 3 密码
    * */
   Integer SMS_TYPE_PASSWORD = 2;
}
