package com.linkmoretech.notice.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.linkmoretech.notice.vo.request.PushMesRequest;

import java.util.List;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
public interface SocketService {

    public void connect(SocketIOClient socketIOClient);

    public void disconnect(SocketIOClient socketIOClient);

    /**
     * @Author GFF
     * @Description isMqMes  是否为mq消息
     * @Date 2019/7/12
     */
    Boolean pushMesList(List<PushMesRequest> list,Boolean isMqMes);

    Boolean pushMesOne(PushMesRequest mesRequest,Boolean isMqMes);

    SocketIOClient getSocketIOClientOne(PushMesRequest userId);

    List<SocketIOClient> getSocketIOClientList(List<PushMesRequest> userIds);

    void savePushMes( PushMesRequest mesRequest);

    void send(SocketIOClient client,PushMesRequest mesRequest);

    Long getLineUserCount();


}
