package com.linkmoretech.notice.service;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/11
 */
public class SocketFactory {

    private SocketFactory(){}

    public SocketService getSocketService(Class clazz){
        return new SocketServiceImpl();
    }



    public static SocketFactory getInstance(){
        return LazySocketFactory.socketFactory;
    }

    private static class LazySocketFactory{
        private static final SocketFactory socketFactory = new SocketFactory();
    }
}
