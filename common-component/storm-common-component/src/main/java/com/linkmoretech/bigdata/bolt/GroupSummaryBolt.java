package com.linkmoretech.bigdata.bolt;

import com.linkmoretech.bigdata.constant.BoltCommonField;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: alec
 * Description: 分组汇总计算单元，用于取得对数据汇总发送
 * 0 1  0 2
 * @date: 11:29 2019-07-15
 */
public class GroupSummaryBolt extends BaseRichBolt {

    private OutputCollector collector;
    private final Map<String, Long> recordData = new HashMap<>();


    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String recordPark = input.getStringByField(BoltCommonField.keyParkCode);
        Integer direction = input.getIntegerByField(BoltCommonField.keyDirection);
        Integer type = input.getIntegerByField(BoltCommonField.keyRenderFlag);
        String key = recordPark + ":" + type;
        Long count = recordData.get(key);
        if (count == null) {
            count = 0L;
        }
        count = count + (direction * 2) -1 ;
        recordData.put(key, count);
        this.collector.emit(new Values(recordPark, type, count));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(BoltCommonField.keyParkCode, BoltCommonField.keyRenderFlag, BoltCommonField.keyCount));
    }
}
