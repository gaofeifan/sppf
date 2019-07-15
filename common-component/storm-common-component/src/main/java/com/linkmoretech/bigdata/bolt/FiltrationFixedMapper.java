package com.linkmoretech.bigdata.bolt;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.bigdata.constant.BoltCommonField;
import org.apache.storm.redis.common.mapper.RedisDataTypeDescription;
import org.apache.storm.redis.common.mapper.RedisLookupMapper;
import org.apache.storm.shade.com.google.common.collect.Lists;
import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.ITuple;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.List;

/**
 * @Author: alec
 * Description: Storm 计算单元
 * 通过查询redis 为该记录添加长租标示
 * 需要去连接查询对redis
 * @date: 10:51 2019-07-15
 */
public class FiltrationFixedMapper implements RedisLookupMapper {

    private RedisDataTypeDescription description;

    public FiltrationFixedMapper() {
        this.description = new RedisDataTypeDescription(
                RedisDataTypeDescription.RedisDataType.HASH,BoltCommonField.keyRenderRecord);
    }
    @Override
    public List<Values> toTuple(ITuple input, Object o) {
        String recordPark = input.getStringByField(BoltCommonField.keyParkCode);
        Integer direction = input.getIntegerByField(BoltCommonField.keyDirection);
        Integer type = o == null ? 0 : 1;
        List<Values> values = Lists.newArrayList();
        values.add(new Values(recordPark, direction, type));
        return values;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        /**
         * 需要弹出对字段，及增加是否是长租标记
         * */
        declarer.declare(new Fields(BoltCommonField.keyParkCode, BoltCommonField.keyDirection, BoltCommonField.keyRenderFlag));
    }

    @Override
    public RedisDataTypeDescription getDataTypeDescription() {
        return this.description;
    }

    @Override
    public String getKeyFromTuple(ITuple tuple) {
        /**
         * 定义Key
         * */
        return tuple.getStringByField(BoltCommonField.keyParkCode);
    }

    @Override
    public String getValueFromTuple(ITuple iTuple) {
        return null;
    }
}
