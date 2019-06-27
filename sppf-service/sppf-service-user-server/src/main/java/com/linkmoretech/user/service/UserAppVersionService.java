package com.linkmoretech.user.service;

import com.linkmoretech.user.entity.UserAppVersion;
import com.linkmoretech.user.vo.UserVersionRequest;

/**
 * 个人版版本管理
 * @author jhb
 * @Date 2019年6月27日 下午1:51:31
 * @Version 1.0
 */
public interface UserAppVersionService {

	UserAppVersion currentAppVersion(Integer source);

	void report(UserVersionRequest uvr, String userId);
   
}
