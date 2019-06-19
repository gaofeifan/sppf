package com.linkmoretech.order.service;

import org.springframework.stereotype.Service;

@Service
public class TokenService {/*
	
	private  Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private WechatConfig wechatConfig;
	
	@Resource
	private RedisService redisService;

	public Token getToken() {
		Token token = null;
//		String json = (String) redisService.get(Constants.RedisKey.WECHAT_TOKEN_KEY.key); 
//		if(StringUtils.isNotBlank(json)){
//			try{
//				token = JsonUtil.toObject(json, Token.class);
//			}catch(Exception e){ 
//				StringBuffer sb = new StringBuffer();
//				StackTraceElement[] stackArray = e.getStackTrace();  
//		        for (int i = 0; i < stackArray.length; i++) {  
//		            StackTraceElement element = stackArray[i];  
//		            sb.append(element.toString() + "\n");  
//		        }   
//				log.info(sb.toString());
//			} 
//		}
//		if(null == token) {
//			token = this.resetToken();
//		}
		return token;
	}  
	
	public Token resetToken(){
		Token token = null;
//		token = WeChat.getToken(wechatConfig.getAppId(), wechatConfig.getAppSecret()); 
//		log.info("获取access_token成功，有效时长{}秒 token:{}", token.getExpiresIn(),token.getAccessToken());  
//		redisService.set(Constants.RedisKey.WECHAT_TOKEN_KEY.key, JsonUtil.toJson(token), Constants.ExpiredTime.WECHAT_TOKEN_EXPIRE.time);
		return token;
	}

	public ResToken resetToken(String appid, String appsecret, String key) {
		Token token = WeChat.getToken(appid, appsecret);
		ResToken object = ObjectUtils.copyObject(token,new ResToken());
		log.info("获取access_token成功，有效时长{}秒 token:{}", token.getExpiresIn(),token.getAccessToken());  
		redisService.set(key, JsonUtil.toJson(object), Constants.ExpiredTime.WECHAT_TOKEN_EXPIRE.time);
		return object;
	}
*/}
