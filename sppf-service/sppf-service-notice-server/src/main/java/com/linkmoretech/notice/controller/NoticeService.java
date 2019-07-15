package com.linkmoretech.notice.controller;

import com.linkmoretech.notice.vo.request.PushMesRequest;

import java.util.List;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
public interface NoticeService {
    Boolean pushMesOne(PushMesRequest mesRequest,boolean isMqMes);

    Boolean pushMesList(List<PushMesRequest> list,boolean isMqMes);
}
