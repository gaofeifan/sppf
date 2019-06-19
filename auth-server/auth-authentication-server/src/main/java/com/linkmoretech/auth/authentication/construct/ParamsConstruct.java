package com.linkmoretech.auth.authentication.construct;

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
    * 小后台登录
    * */
   String LOGIN_PLATFORM = "/platform/login";

   /**
    * 管理版密码登录
    * */
   String LOGIN_MANAGE_PASSWORD = "/manage/login";

   /**
    * 管理版手机号登录
    * */
   String LOGIN_MANAGE_MOBILE = "/manage/login-mobile";

   /**
    * json登录不需要跳转至登录界面，需要提示未登录信息，默认提示地址
    * */
   String NO_LOGIN_TIP_INFO = "/no-login";

   String LOGIN_MOBILE_PERSONAL = "/personal/login-mobile";
}
