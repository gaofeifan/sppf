package com.linkmoretech.bigdata;

import org.apache.storm.redis.common.mapper.RedisDataTypeDescription;
import org.apache.storm.redis.common.mapper.RedisStoreMapper;
import org.apache.storm.tuple.ITuple;

/**
 * @Author: alec
 * Description:
 * @date: 17:08 2019-07-12
 */
public class LocalhostRecordRedisStoreBolt  implements RedisStoreMapper {


    private RedisDataTypeDescription description;

    private final String hashKey = "p";

    public LocalhostRecordRedisStoreBolt() {
        description = new RedisDataTypeDescription(
                RedisDataTypeDescription.RedisDataType.HASH, hashKey);
    }

    @Override
    public RedisDataTypeDescription getDataTypeDescription() {
        return description;
    }

    @Override
    public String getKeyFromTuple(ITuple tuple) {
        return tuple.getStringByField("source");
    }

    @Override
    public String getValueFromTuple(ITuple tuple) {
        return tuple.getIntegerByField("totalCount") + "";
    }
}
