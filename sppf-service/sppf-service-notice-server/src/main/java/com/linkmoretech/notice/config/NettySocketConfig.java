package com.linkmoretech.notice.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/9
 */
@Configuration
public class NettySocketConfig {

    @Autowired
    private NettyProperties nettyProperties;

    @Bean
    public SocketIOServer socketIOServer(){
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setHostname(nettyProperties.getHort());
        configuration.setPort(nettyProperties.getPort());
//		RedissonClient redissonClient = Redisson.create(config);
//		configuration.setStoreFactory(new RedissonStoreFactory(redissonClient));
        SocketIOServer server = new SocketIOServer(configuration);
//		server.setPipelineFactory(new CustomSocketIOChannelInitializer());
//		CustomSocketIOChannelInitializer customSocketIOChannelInitializer = new CustomSocketIOChannelInitializer(configuration);
//		server.setPipelineFactory(customSocketIOChannelInitializer);
        return  server;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}
