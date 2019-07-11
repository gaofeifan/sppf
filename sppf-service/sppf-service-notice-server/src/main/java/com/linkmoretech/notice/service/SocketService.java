package com.linkmoretech.notice.service;

import com.corundumstudio.socketio.SocketIOClient;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
public interface SocketService {

    public void connect(SocketIOClient socketIOClient);

    public void disconnect(SocketIOClient socketIOClient);
}
