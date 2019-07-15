package com.linkmoretech.notice.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.linkmoretech.notice.vo.request.PushMesRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/12
 *//*
@Slf4j
public class RabbitSocketServiceImpl extends AbstractSocketDecorator {
    public RabbitSocketServiceImpl(SocketService socketService) {
        super(socketService);
    }

    @Override
    public Boolean pushMesList(List<PushMesRequest> list) {
        return super.pushMesList(list);
    }

    @Override
    public Boolean pushMesOne(PushMesRequest mesRequest) {
        SocketIOClient socketIOClient = super.getSocketIOClientOne(mesRequest);
        if(socketIOClient == null){
            log.info("该用户长连接不在本服务上 直接丢弃");
            return true;
        }
        Boolean aBoolean = super.pushMesOne(mesRequest);

        return true;
    }
}*/
