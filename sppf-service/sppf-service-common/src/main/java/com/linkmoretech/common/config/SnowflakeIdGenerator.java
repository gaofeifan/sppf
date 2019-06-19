package com.linkmoretech.common.config;

/**
 * ID生成策略
 * @Author: alec
 * @Description:
 * @date: 下午3:37 2019/4/10
 */
public class SnowflakeIdGenerator {

    private final long START_TIME = 1498608000000L;

    private final long WORKER_ID_BITS = 5L;
    /*数据标识id所占的位数*/
    private final long DATA_CENTER_ID_BITS = 5L;

    private final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);

    private final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);
    // 序列在id中占的位数
    private final long SEQUENCE_BITS = 12L;
    // 机器ID 左移位数 - 12 (即末 sequence 所占用的位数)
    private final long WORKER_ID_MOVE_BITS = SEQUENCE_BITS;
    // 数据标识id 左移位数 - 17(12+5)
    private final long DATA_CENTER_ID_MOVE_BITS = SEQUENCE_BITS + WORKER_ID_BITS;
    // 时间截向 左移位数 - 22(5+5+12)
    private final long TIME_STAMP_MOVE_BITS = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    // 生成序列的掩码(12位所对应的最大整数值)，这里为4095 (0b111111111111=0xfff=4095)
    private final long SEQUEUE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;
    /**
     * 数据中心ID(0~31)
     */
    private long dataCenterId;
    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("DataCenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized long nextId() {
        long timestamp = currentTime();
        //如果当前时间小于上一次ID生成的时间戳: 说明系统时钟回退过 - 这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUEUE_MASK;
            //毫秒内序列溢出 即 序列 > 4095
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = blockTillNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }
        //上次生成ID的时间截
        lastTimestamp = timestamp;
        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - START_TIME) << TIME_STAMP_MOVE_BITS) //
                | (dataCenterId << DATA_CENTER_ID_MOVE_BITS) //
                | (workerId << WORKER_ID_MOVE_BITS) //
                | sequence;
    }
    // 阻塞到下一个毫秒 即 直到获得新的时间戳
    protected long blockTillNextMillis(long lastTimestamp) {
        long timestamp = currentTime();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime();
        }
        return timestamp;
    }
    // 获得以毫秒为单位的当前时间
    protected long currentTime() {
        return System.currentTimeMillis();
    }
}
