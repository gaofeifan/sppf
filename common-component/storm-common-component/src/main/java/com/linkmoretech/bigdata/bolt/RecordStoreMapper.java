package com.linkmoretech.bigdata.bolt;

import org.apache.storm.redis.common.mapper.RedisDataTypeDescription;
import org.apache.storm.redis.common.mapper.RedisStoreMapper;
import org.apache.storm.tuple.ITuple;

/**
 * @Author: alec
 * Description:
 * @date: 19:41 2019-07-12
 */
public class RecordStoreMapper implements RedisStoreMapper {

    private RedisDataTypeDescription description;

    private final String hashKey = "wordCount";

    public RecordStoreMapper() {
        description = new RedisDataTypeDescription(
                RedisDataTypeDescription.RedisDataType.HASH, hashKey);
    }


    @Override
    public RedisDataTypeDescription getDataTypeDescription() {
        return null;
    }

    @Override
    public String getKeyFromTuple(ITuple iTuple) {
        return null;
    }

    @Override
    public String getValueFromTuple(ITuple iTuple) {
        return null;
    }
}
