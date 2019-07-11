package com.linkmoretech.notice.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.linkmoretech.notice.service.SocketService;
import com.linkmoretech.notice.vo.request.PushMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/9
 */
@Component
@Slf4j
public class SocketServer {

    @Resource(name="socketService")
    private SocketService socketService;
    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    public SocketServer(SocketIOServer socketIOServer){
        this.socketIOServer = socketIOServer;
    }


    @OnConnect
    public void onConnect(SocketIOClient socketIOClient){
        log.info("有连接进入");
        socketService.connect(socketIOClient);
    }
    @OnDisconnect
    public void OnDisconnect(SocketIOClient socketIOClient){
        log.info("有连接断开");
        socketService.disconnect(socketIOClient);
    }

    @OnEvent(value = "message")
    public void onEnvent(SocketIOClient client, AckRequest request, PushMessageRequest data){

    }


}
