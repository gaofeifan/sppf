package com.linkmoretech.notice.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }

    public String getUrl(){
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress()+":"+serverPort;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
