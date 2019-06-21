package com.linkmoretech.order.service;

import org.springframework.stereotype.Service;
import com.linkmoretech.order.common.request.ReqTemplateMsg;
import com.linkmoretech.order.common.response.ResToken;
import com.linkmoretech.order.common.response.ResWechatUserList;

/**
 * 微信 服务处理，消息转换等
 * @author jhb
 * @version 1.0
 */
@Service
public interface WechatService {

	/**
	 * 获取ticket
	 * @param actionName
	 * @param sceneId
	 * @return
	 */
	public String getTicket(String actionName,Long sceneId);

	/**
	 * @Description  根据appid  appsecret  key刷新
	 * @Author   jhb 
	 * @Version  v2.0
	 */
	public ResToken resetToken(String appid, String appsecret, String key);

	/**
	 * @Description  推送微信消息
	 * @Author   jhb 
	 * @Version  v2.0
	 */
	public void pushTemplateMsg(ReqTemplateMsg msg);

	/**
	 * @Description  获取公众号关注的用户列表
	 * @Author   jhb 
	 * @Version  v2.0
	 */
	public ResWechatUserList getUserList(String token);
}



