package com.linkmoretech.notice.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/9
 */
@Component
@Slf4j
public class NettyServerRunner implements CommandLineRunner {
    private final SocketIOServer server;

    @Autowired
    public NettyServerRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("启动netty-socket");
        server.start();

    }
}
