package com.linkmoretech.notice.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.linkmoretech.notice.vo.request.PushMesRequest;

import java.util.List;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 *//*
public abstract class AbstractSocketDecorator implements SocketService {

    private SocketService socketService;

    public AbstractSocketDecorator(SocketService socketService){
        this.socketService = socketService;
    }

    public void connect(SocketIOClient socketIOClient){
        this.socketService.connect(socketIOClient);
    }

    public void disconnect(SocketIOClient socketIOClient){
        this.socketService.disconnect(socketIOClient);
    }

    @Override
    public Boolean pushMesList(List<PushMesRequest> list) {
        return this.socketService.pushMesList(list);
    }

    @Override
    public Boolean pushMesOne(PushMesRequest mesRequest) {
        return this.socketService.pushMesOne(mesRequest);
    }

    @Override
    public SocketIOClient getSocketIOClientOne(PushMesRequest userId) {
        return this.socketService.getSocketIOClientOne(userId);
    }

    @Override
    public List<SocketIOClient> getSocketIOClientList(List<PushMesRequest> userIds) {
        return this.socketService.getSocketIOClientList(userIds);
    }

    @Override
    public void savePushMes(PushMesRequest mesRequest) {
        this.socketService.savePushMes(mesRequest);
    }

    @Override
    public void send(SocketIOClient client, PushMesRequest mesRequest) {
        this.socketService.send( client, mesRequest);
    }

    @Override
    public Long getLineUserCount() {
        return this.socketService.getLineUserCount();
    }
}
    */
