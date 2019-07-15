package com.linkmoretech.bigdata.bolt;

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
 * Description: 获取设备对应的车场及出入口表示
 * @date: 19:51 2019-07-12
 */
public class RecordRedisGroupMapper  implements RedisLookupMapper {

    private RedisDataTypeDescription description;

    private final String hashKey = "device";

    public RecordRedisGroupMapper() {
        description = new RedisDataTypeDescription(
                RedisDataTypeDescription.RedisDataType.HASH, hashKey);
    }

    @Override
    public List<Values> toTuple(ITuple input, Object value) {
        String renterValue = getKeyFromTuple(input);
        Integer count = input.getIntegerByField("count");
        List<Values> values = Lists.newArrayList();
        values.add(new Values(renterValue,count, value));
        return values;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("source", "count",  "device"));
    }

    @Override
    public RedisDataTypeDescription getDataTypeDescription() {
        return description;
    }

    @Override
    public String getKeyFromTuple(ITuple iTuple) {
        return iTuple.getStringByField("source");
    }

    @Override
    public String getValueFromTuple(ITuple iTuple) {
        return null;
    }
}
