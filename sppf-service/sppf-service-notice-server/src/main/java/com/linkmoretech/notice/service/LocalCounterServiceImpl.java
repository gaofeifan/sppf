package com.linkmoretech.notice.service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/10
 */
public class LocalCounterServiceImpl  implements CounterService{

    private AtomicLong clientCount = new AtomicLong(0);

    @Override
    public void add() {
        synchronized (this) {
            clientCount.getAndDecrement();
        }
    }

    @Override
    public void sub() {
        synchronized (this) {
            clientCount.getAndDecrement();
        }
    }
}
