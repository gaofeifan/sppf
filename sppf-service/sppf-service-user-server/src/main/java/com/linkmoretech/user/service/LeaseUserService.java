package com.linkmoretech.user.service;

import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.user.vo.response.LeaseUserListResponse;

/**
 * @Author: alec
 * Description: 长租用户
 * @date: 16:35 2019-05-23
 */
public interface LeaseUserService {

    /**
     * 查询长租车位分页数据
     * @param pageSearchRequest 查询条件
     * @return 返回分页参数
     * */
    PageDataResponse<LeaseUserListResponse> searchPage(PageSearchRequest pageSearchRequest);
}
