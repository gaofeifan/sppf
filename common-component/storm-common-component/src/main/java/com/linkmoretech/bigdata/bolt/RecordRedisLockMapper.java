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
 * Description: 这个一个验证车牌号是否是固定车位的slot
 * 通过对比redis中已经设定好的记录匹配
 * 将匹配的结果交给下一个solt
 * @date: 18:20 2019-07-12
 */
public class RecordRedisLockMapper implements RedisLookupMapper {

    private RedisDataTypeDescription description;

    private final String hashKey = "renter";

    public RecordRedisLockMapper() {
        description = new RedisDataTypeDescription(
                RedisDataTypeDescription.RedisDataType.HASH, hashKey);
    }

    @Override
    public List<Values> toTuple(ITuple input, Object value) {
        String renterValue = getKeyFromTuple(input);
        List<Values> values = Lists.newArrayList();
        values.add(new Values(renterValue, value));
        return values;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        /**
         * 定义输出字段及判断某车场，某车牌是否数长租车位
         * */
        outputFieldsDeclarer.declare(new Fields("renter", "count"));
    }

    @Override
    public RedisDataTypeDescription getDataTypeDescription() {
        return description;
    }

    @Override
    public String getKeyFromTuple(ITuple tuple) {
        return tuple.getStringByField("source") + ":" + tuple.getStringByField("carNo");
    }

    @Override
    public String getValueFromTuple(ITuple tuple) {
        return null;
    }
}
