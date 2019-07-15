package com.linkmoretech.bigdata;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.redis.bolt.RedisLookupBolt;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.redis.common.mapper.RedisDataTypeDescription;
import org.apache.storm.redis.common.mapper.RedisLookupMapper;
import org.apache.storm.shade.com.google.common.collect.Lists;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.ITuple;
import org.apache.storm.tuple.Values;

import java.util.List;

/**
 * @Author: alec
 * Description: redis 存储 查询
 * @date: 16:53 2019-07-12
 */
public class LocalRecordRedisBolt implements RedisLookupMapper {

    private RedisDataTypeDescription description;

    private final String hashKey = "p";

    public LocalRecordRedisBolt() {
        description = new RedisDataTypeDescription(
                RedisDataTypeDescription.RedisDataType.HASH, hashKey);
    }

    @Override
    public List<Values> toTuple(ITuple iTuple, Object o) {
        String value = getKeyFromTuple(iTuple);
        Integer count = (value == null ? 0 : 1 );
        List<Values> values = Lists.newArrayList();
        values.add(new Values(count, o));
        return values;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("carCount", "obj"));
    }

    @Override
    public RedisDataTypeDescription getDataTypeDescription() {
        return description;
    }

    @Override
    public String getKeyFromTuple(ITuple tuple) {
        String key = tuple.getStringByField("source") + ":" ;
        key = key + tuple.getStringByField("carNo");
        return key;
    }

    @Override
    public String getValueFromTuple(ITuple iTuple) {
        return null;
    }
}
