package com.linkmoretech.notice.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
@Service(value = "socketRedisService")
public class SocketRedisServiceImpl extends SocketDecorator {
    public SocketRedisServiceImpl(SocketService socketService) {
        super(socketService);
    }



}
