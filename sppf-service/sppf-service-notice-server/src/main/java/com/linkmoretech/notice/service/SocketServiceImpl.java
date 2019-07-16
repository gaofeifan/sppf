package com.linkmoretech.notice.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.linkmoretech.common.util.JsonUtil;
import com.linkmoretech.common.util.TaskPool;
import com.linkmoretech.notice.config.RabbitConfig;
import com.linkmoretech.notice.entity.Notice;
import com.linkmoretech.notice.enums.AgingTypeEnum;
import com.linkmoretech.notice.resposity.NoticeResposity;
import com.linkmoretech.notice.vo.request.PushMesRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
@Service(value = "socketService")
@Slf4j
public class SocketServiceImpl implements SocketService{

//	@Autowired
//	private NoticeResposity noticeResposity;
	@Autowired
    private RabbitConfirmCallback rabbitConfirmCallback;
    @Autowired
    private RabbitTemplate rabbitTemplate;
//    @Autowired
//    private AmqpTemplate amqpTemplate;

    private String sendMessage = "acceptMes";

    private static final int taskCount = 2;
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


    @Override
    public Boolean pushMesList(List<PushMesRequest> list,Boolean isMqMes) {
        log.info("推送多人消息");
        TaskPool.getInstance().task(()->{
            List<SocketIOClient> clients = getSocketIOClientList(list);

        });


        return true;
    }

    @Override
    public Boolean pushMesOne(PushMesRequest mesRequest,Boolean isMqMes) {
        log.info("推送单人消息");
        SocketIOClient client = getSocketIOClientOne(mesRequest);
        if(client != null){
            log.info("用户在当前服务器上直接推送");
            send(client,mesRequest);
            saveDB(mesRequest);
           return true;
        }
        if(!isMqMes){
            log.info("非MQ广播消息");
            log.info("用户不在当前服务器上 进入广播模式");
            //TODO 将消息保存到redis中
            saveRedisPushMes(mesRequest);
            fanoutPushMes(mesRequest);
        }
        log.info("MQ广播消息 用户不存在当前服务器 遗弃信息");
        return true;
    }

    /**
     * @Author GFF
     * @Description  广播推送的消息
     * @Date 2019/7/12
     */
    private void fanoutPushMes(PushMesRequest mesRequest) {
        if(mesRequest.getAgingType() != AgingTypeEnum.SEND.getCode()){
        	rabbitTemplate.convertAndSend(RabbitConfig.PUSH_FANOUT_EXCHANGE,null,mesRequest);
        }else {
        	rabbitTemplate.setConfirmCallback(rabbitConfirmCallback);
        	rabbitTemplate.setReturnCallback(rabbitConfirmCallback);
        	rabbitTemplate.convertAndSend(RabbitConfig.PUSH_FANOUT_EXCHANGE,null,mesRequest);
        }
    }

    /**
     * @Author GFF
     * @Description  将消息保存到redis中
     * @Date 2019/7/11
     */
    private void saveRedisPushMes(PushMesRequest pushMesRequest){
        // 判断时效性
       if(pushMesRequest.getAgingType() != AgingTypeEnum.SEND.getCode()){
           // 设置当前任务的次数
           // 保存到redis中

       }
    }

    private void saveDB(PushMesRequest pushMesRequest) {
    	log.info("保存消息到mysql");
	   Notice notice = new Notice();
	   notice.setContent(pushMesRequest.getContent());
	   notice.setAgingType(pushMesRequest.getAgingType());
	   notice.setCreateTime(new Date());
	   notice.setPushName(pushMesRequest.getPushName());
	   notice.setPushType(pushMesRequest.getPushType());
	   notice.setState(pushMesRequest.getState());
	   notice.setPrivateField(JsonUtil.toJson(pushMesRequest.getPrivateField()));
	   notice.setTitle(pushMesRequest.getTitle());
	   notice.setUserId(pushMesRequest.getUserId());
	   notice.setUuid(pushMesRequest.getUuid());
	   notice.setVersion(pushMesRequest.getVersion());
//	   this.noticeResposity.save(notice);
    }


    /**
     * @Author GFF
     * @Description   定时任务方法
     *                设置2分钟的定时任务 将redis中的消息通过广播模式发送   如果任务存活了指定次数后仍没有消费 持久化到数据库中
     * @Date 2019/7/11
     */
    public void pushMesRedisTask(){
        // 从redis中获取任务
        // 将任务重新发送到mq的队列中
        // 将redis消息任务次数加1
        // 判断redis中的次数是否超过了阈值
            // 是 将该数据持久化到数据库 等待该用户重新连接   将该数据删除
            // 否 将次数+1 重新放入到redis中


    }

    private String getSocketParam(SocketIOClient socketIOClient) {
        Map<String, List<String>> params = socketIOClient.getHandshakeData().getUrlParams();
        List<String> list = params.get("userId");
        if(list != null && list.size() != 0){
            return list.get(0);
        }
        return null;
    }
    @Override
    public SocketIOClient getSocketIOClientOne(PushMesRequest userId){
        return clientMap.get(userId.getUserId()+"");
    }

    @Override
    public List<SocketIOClient> getSocketIOClientList(List<PushMesRequest> userIds){
        List<SocketIOClient> sockets = new ArrayList<>();
        SocketIOClient s = null;
        for (PushMesRequest user: userIds) {
            s = getSocketIOClientOne(user);
            if(s != null){
                sockets.add(s);
            }
        }
        return sockets;
    }

    @Override
    public void savePushMes(PushMesRequest mesRequest) {
        // TODO 持久化到数据库
        // TODO 将redis中存在的数据删除
    }

    @Override
    public void send(SocketIOClient client, PushMesRequest mesRequest) {
    	log.info("推送到客户端>>>>>"+JsonUtil.toJson(mesRequest));
        client.sendEvent(sendMessage,mesRequest);
        savePushMes(mesRequest);
        //TODO 对 需要客户端确认收到消息的响应
    }

    @Override
    public Long getLineUserCount() {
        return null;
    }
}
