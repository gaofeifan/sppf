package com.linkmoretech.bigdata;

import com.alibaba.fastjson.JSONObject;
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
 * Description: 将数据汇总
 * @date: 16:45 2019-07-12
 */
public class LocalRecordGroupBolt extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {

        /**
         *  计算单元 解析数据，并按汇总
         * */
       String record = input.getStringByField("record");

       System.out.println(record);
       /**
        * 解析json 数据
        * */
        JSONObject jsonObject = JSONObject.parseObject(record);
        Object sourceObj = jsonObject.get("resource");
        Object carPlace = jsonObject.get("carNo");
        if (sourceObj != null && carPlace != null) {
            System.out.println("弹射数据");
            /**
             * 将原数据，汇总和数据， 资源ID， 车牌号 弹射出去
             * */
            collector.emit(new Values(sourceObj, carPlace));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("source", "carNo"));
    }
}
