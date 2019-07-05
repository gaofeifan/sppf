package com.linkmoretech.versatile.service;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.vo.response.UserVersionPageResponse;

/**
 * 用户版本列表
 * @author jhb
 * @Date 2019年6月27日 下午1:51:31
 * @Version 1.0
 */
public interface UserVersionService {
	
	/**
	 * 列表
	 * @param searchRequest
	 * @return
	 */
	PageDataResponse<UserVersionPageResponse> searchPage(PageSearchRequest searchRequest);
   
}
