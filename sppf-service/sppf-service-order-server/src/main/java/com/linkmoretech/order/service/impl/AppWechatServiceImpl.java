package com.linkmoretech.order.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.enums.StatusEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.util.JsonUtil;
import com.linkmoretech.order.common.request.ReqAppWechatOrder;
import com.linkmoretech.order.common.response.ResAppWechatOrder;
import com.linkmoretech.order.common.response.ResFans;
import com.linkmoretech.order.config.AppWechatConfig;
import com.linkmoretech.order.pay.PayConstants;
import com.linkmoretech.order.pay.wechat.HttpsRequest;
import com.linkmoretech.order.pay.wxpay.PaySign;
import com.linkmoretech.order.pay.wxpay.WeixinPay;
import com.linkmoretech.order.service.AppWechatService;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Service
public class AppWechatServiceImpl implements AppWechatService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AppWechatConfig appWechatConfig;
	
	private static final String GET = "GET";
	
	//网页授权OAuth2.0获取token
	private static final String GET_OAUTH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
 
	//网页授权OAuth2.0获取用户信息
	private static final String GET_OAUTH_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

	
	//网页授权OAuth2.0获取token
	private  String getOAuthTokenUrl(String code ){
		return String.format(GET_OAUTH_TOKEN, appWechatConfig.getAppId(), appWechatConfig.getAppSecret(), code);
	}
	
	//网页授权OAuth2.0获取用户信息
	public static String getOAuthUserinfoUrl(String token ,String openid){
		return String.format(GET_OAUTH_USERINFO, token, openid);
	}
	
	
	/**
	 * 获取OAuth2.0 UserInfo
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	@Override
	public ResFans getWechatFans(String code) {
		String tokenUrl = getOAuthTokenUrl(code);
		JSONObject json = HttpsRequest.parse(tokenUrl, GET, null); 
		if (null != json && json.get("errcode") == null) {
			try {
				String openid = json.getString("openid");
				String token = json.getString("access_token");
				String url = getOAuthUserinfoUrl(token, openid);
				json =  HttpsRequest.parse(url,GET,null);
			} catch (JSONException e) {
				log.info(".... JSONException = {}",e.getMessage());
				json = null;
			}
		}else {
			try {
				throw new CommonException(ResponseCodeEnum.ERROR,StatusEnum.ACCOUNT_WECHAT_LOGIN_ERROR.label);
			} catch (CommonException e) {
				log.info(".... CommonException = {}",e.getMessage());
				e.printStackTrace();
			}
		} 
		log.info("get wechat fans json:{}",JsonUtil.toJson(json));
		ResFans rf = null;
		if(json!=null && json.get("errcode") == null) {
			String openid = json.getString("openid"); 
			String nickname = json.getString("nickname");
			String headimgurl = json.getString("headimgurl"); 
			String unionid = json.getString("unionid"); 
			rf = new ResFans();
			rf.setId(openid);
			rf.setHeadurl(headimgurl);
			rf.setNickname(nickname);
			rf.setUnionid(unionid);
			rf.setCreateTime(new Date());
			rf.setStatus((short)1);
			rf.setRegisterStatus((short)0); 
		} 
		return rf;
	}

	@Override
	public ResAppWechatOrder order(ReqAppWechatOrder wechat) throws JDOMException, IOException {
		return WeixinPay.orderPay(wechat.getAddress(), wechat.getNumber(),
				PayConstants.BODY_ORDER, wechat.getAmount()); 
	} 
	
	public Boolean verify(String json) {
		Map<String,String> map = JsonUtil.toObject(json, HashMap.class);
		return PaySign.isValidSign(PaySign.strmapToTreeMap(map));
	}
}

