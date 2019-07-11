package com.linkmoretech.notice.service;

import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
@Service(value = "socketRedisService")
public class SocketRedisServiceImpl extends SocketDecorator {
    public SocketRedisServiceImpl(SocketService socketService) {
        super(socketService);
    }

    @Override
    //TODO 将服务标识 用户id关系表保存到redis中 用于分布式操作
    public void connect(SocketIOClient socketIOClient) {
        super.connect(socketIOClient);
    }

    @Override
    public void disconnect(SocketIOClient socketIOClient) {
        super.disconnect(socketIOClient);
    }
}
