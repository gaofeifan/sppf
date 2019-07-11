package com.linkmoretech.versatile.service;

import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.versatile.vo.request.UnusualLogRequest;
import com.linkmoretech.versatile.vo.response.UnusualLogPageResponse;

/**
 * 异常日志服务层
 * @Author: alec
 * @Description:
 * @date: 8:32 PM 2019/4/29
 */
public interface UnusualLogService {
	
	/**
	 * 上报日志
	 * @param unusualLogRequest
	 */
	void save(UnusualLogRequest unusualLogRequest);
    
    /**
     * 分页查询列表
     * @param pageSearchRequest 分页参数
     * @return 分页查询结果
     * */
    PageDataResponse<UnusualLogPageResponse> searchPage(PageSearchRequest pageSearchRequest);

}
