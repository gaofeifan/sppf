package com.linkmoretech.bigdata;

import clojure.lang.Obj;
import com.alibaba.fastjson.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 18:10 2019-07-12
 */
public class TestRedisBolt extends BaseRichBolt {
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    @Override
    public void execute(Tuple input) {

       int c =  input.getIntegerByField("carCount");
        Object o = input.getValues();

        System.out.println("c " + c);
        System.out.println("o " + JSONObject.toJSONString(o));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
