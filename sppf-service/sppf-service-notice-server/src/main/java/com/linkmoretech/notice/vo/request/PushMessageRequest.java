package com.linkmoretech.notice.vo.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/9
 */
@ApiModel
@Data
public class PushMessageRequest {
    private Long userId;

    private String client;

    // 消息内容
    private String content;
    
    //	消息id  
    private String uuid;
    
    // 0 心跳消息  1收到消息的回调  2客户端发给服务端的消息
    private int type;
    
    

}
