package com.linkmoretech.user.service;

import java.util.List;

import com.linkmoretech.user.vo.UserGuideResponse;

/**
 * 用户指引
 * @author jhb
 * @Date 2019年6月27日 下午1:51:31
 * @Version 1.0
 */
public interface UserGuideService {
	/**
	 * 查询用户指南
	 * @param language
	 * @return
	 */
	List<UserGuideResponse> find(String language);

	
}
