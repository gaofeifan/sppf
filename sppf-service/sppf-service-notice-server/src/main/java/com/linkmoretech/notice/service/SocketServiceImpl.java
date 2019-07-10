package com.linkmoretech.notice.service;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
@Service(value = "socketService")
@Slf4j
public class SocketServiceImpl implements SocketService{

    private static final Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    @Override
    public void connect(SocketIOClient socketIOClient) {
        String userId = getSocketParam(socketIOClient);
        log.info("有连接进入"+userId);
        clientMap.put(userId,socketIOClient);
    }

    @Override
    public void disconnect(SocketIOClient socketIOClient) {
        String userId = getSocketParam(socketIOClient);
        log.info("有连接断开"+userId);
        clientMap.remove(userId);
        socketIOClient.disconnect();
    }

    private String getSocketParam(SocketIOClient socketIOClient) {
        Map<String, List<String>> params = socketIOClient.getHandshakeData().getUrlParams();
        List<String> list = params.get("userId");
        if(list != null && list.size() != 0){
            return list.get(0);
        }
        return null;
    }
}
