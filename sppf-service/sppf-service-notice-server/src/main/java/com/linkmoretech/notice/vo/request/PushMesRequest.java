package com.linkmoretech.notice.vo.request;

import com.linkmoretech.common.enums.PushType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PushMesRequest implements Serializable {

    /**
     * 用户id  推送的唯一标识
     */
	
    private Long userId;
    
    private String uuid;

    /**
     *   预留字段  版本用于标识不同版本的推送(个人版,管理版)
     */
    private String version;

    /**
     *  内容 推送信息内容
     */
    private String content;

    /**
     * 推送标题
     */
    private String title;

    /**
     * 推送类型
     */
    private String pushType;
    
    private String pushName;

    /**
     * 操作成功标识 1成功 2失败
     */
    private Integer state = 1;

    /**
     * 预留字段  私有字段  当前字段不满足业务时可以使用
     */
    private Map<String,Object>  privateField;

    /**
     * 时效类型
     * 0(默认)当前用户在线发送不在线就丢弃
     * 1保证服务端一定推送出去,客户端由于某些原因没有接收到不做考虑
     * 2保证用户一定收到该条消息(需要客户端收到消息后进行确认 否则会重复投递)
     */
    private int agingType = 0;

}
