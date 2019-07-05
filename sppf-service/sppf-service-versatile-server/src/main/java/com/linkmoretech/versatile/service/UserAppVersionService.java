package com.linkmoretech.versatile.service;

import java.util.List;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.entity.UserAppVersion;
import com.linkmoretech.versatile.vo.request.UserAppVersionRequest;
import com.linkmoretech.versatile.vo.response.UserAppVersionPageResponse;
import com.linkmoretech.versatile.vo.response.UserVersionRequest;

/**
 * 个人版版本管理
 * @author jhb
 * @Date 2019年6月27日 下午1:51:31
 * @Version 1.0
 */
public interface UserAppVersionService {
	/**
	 * 查询最新版本
	 * @param source
	 * @return
	 */
	UserAppVersion currentAppVersion(Integer source);
	
	/**
	 * 上报用户版本
	 * @param uvr
	 * @param userId
	 */
	void report(UserVersionRequest uvr, Long userId);
	/**
	 * 新增个人版版本
	 * @param version
	 */
	void saveApp(UserAppVersionRequest version);
	
	/**
	 * 更新个人版版本
	 * @param version
	 */
	void updateApp(UserAppVersionRequest version);
	/**
	 * 删除个人版APP
	 * @param ids
	 */
	void deleteAppById(List<Long> ids);
	/**
	 * 列表
	 * @param searchRequest
	 * @return
	 */
	PageDataResponse<UserAppVersionPageResponse> searchPage(PageSearchRequest searchRequest);
   
}
