package com.linkmoretech.notice.controller;

import com.linkmoretech.notice.enums.MqMesEnum;
import com.linkmoretech.notice.service.SocketService;
import com.linkmoretech.notice.vo.request.PushMesRequest;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/9
 */
@RestController
@Slf4j
@Api(tags = "消息服务", value = "notice" )
public class NoticeController {

    @Resource
    private NoticeService noticeService;
    
    @PostMapping(value = "/push-one")
    public Boolean pushMesOne(@RequestBody PushMesRequest mesRequest){
    	mesRequest.setUuid(getUUid(null));
       return noticeService.pushMesOne(mesRequest,MqMesEnum.NO.isFlag());
    }

    public Boolean pushMesList(@RequestBody List<PushMesRequest> list){
    	for (PushMesRequest pushMesRequest : list) {
    		pushMesRequest.setUuid(getUUid(null));
		}
       return noticeService.pushMesList(list,MqMesEnum.NO.isFlag());
    }
    private String getUUid(String str){
		if (str == null) {
			return UUID.randomUUID().toString();
		} else {
			return UUID.nameUUIDFromBytes(str.getBytes()).toString();
		}
    }
}
