package com.linkmoretech.bigdata.bolt;

import com.linkmoretech.bigdata.constant.BoltCommonField;
import org.apache.storm.redis.common.mapper.RedisDataTypeDescription;
import org.apache.storm.redis.common.mapper.RedisStoreMapper;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.ITuple;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 14:14 2019-07-15
 */
public class PersistenceMapper implements RedisStoreMapper {

    private RedisDataTypeDescription description;

    private final String hashKey = "carCount";


    public PersistenceMapper() {

        description = new RedisDataTypeDescription(
                RedisDataTypeDescription.RedisDataType.HASH, hashKey);
    }

    @Override
    public RedisDataTypeDescription getDataTypeDescription() {
        return description;
    }

    @Override
    public String getKeyFromTuple(ITuple tuple) {
        Integer type = tuple.getIntegerByField(BoltCommonField.keyRenderFlag);
        String key = tuple.getStringByField(BoltCommonField.keyParkCode) + "_" + type;
        return key;
    }

    @Override
    public String getValueFromTuple(ITuple tuple) {
        return tuple.getLongByField(BoltCommonField.keyCount) + "";
    }
}
