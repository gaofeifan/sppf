package com.linkmoretech.notice.service;

import com.corundumstudio.socketio.SocketIOClient;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
public abstract class SocketDecorator implements SocketService {

    private SocketService socketService;

    public SocketDecorator(SocketService socketService){
        this.socketService = socketService;
    }

    public void connect(SocketIOClient socketIOClient){
        this.socketService.connect(socketIOClient);
    }

    public void disconnect(SocketIOClient socketIOClient){
        this.socketService.disconnect(socketIOClient);
    }
}
