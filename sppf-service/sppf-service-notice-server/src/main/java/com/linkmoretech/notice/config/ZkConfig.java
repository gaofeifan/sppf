package com.linkmoretech.notice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/9
 */
@Slf4j
public class ZkConfig {

    final CountDownLatch latch = new CountDownLatch(1);

    public ZooKeeper zooKeeper = null;
    private String host = "140.143.5.210";

    private String port = "2181";

    public void init() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(host + port,5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState() == KeeperState.SyncConnected) {
                    log.info("建立连接");
                    latch.countDown();
                }
            }
        });
        latch.await();

    }
}
