package com.linkmoretech.notice.service;

import com.linkmoretech.common.config.RedisService;
import com.linkmoretech.notice.controller.NoticeService;
import com.linkmoretech.notice.enums.MqMesEnum;
import com.linkmoretech.notice.vo.request.PushMesRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
@Service
public class NoticeServiceImpl implements NoticeService {

	@Resource(name="socketService")
	private SocketService socketService;
//    SocketFactory instance = SocketFactory.getInstance();


    @Override
    public Boolean pushMesOne(PushMesRequest mesRequest,boolean isMqMes) {
        Boolean falg = socketService.pushMesOne(mesRequest,isMqMes);
        return null;
    }

    @Override
    public Boolean pushMesList(List<PushMesRequest> list,boolean isMqMes) {
        Boolean falg = socketService.pushMesList(list,isMqMes);
        return null;
    }
    
    
}
