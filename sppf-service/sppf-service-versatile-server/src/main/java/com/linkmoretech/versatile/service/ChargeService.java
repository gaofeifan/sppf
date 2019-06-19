package com.linkmoretech.versatile.service;

import com.linkmoretech.versatile.vo.response.ChargeListResponse;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 17:39 2019-05-20
 */
public interface ChargeService {

    /**
     * 计费下拉列表
     * @return 计费列表
     * */
    List<ChargeListResponse> list();
}
