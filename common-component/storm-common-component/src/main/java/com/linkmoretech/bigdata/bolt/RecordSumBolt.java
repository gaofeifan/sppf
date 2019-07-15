package com.linkmoretech.bigdata.bolt;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.List;
import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 19:59 2019-07-12
 */
public class RecordSumBolt extends BaseRichBolt {

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    @Override
    public void execute(Tuple input) {

        List<Object> objectList =   input.getValues();
        if (objectList != null && objectList.size() == 3) {
            /**
             * 长租车位
             * */
            int fixCount = (Integer) objectList.get(1);

            /**
             * 得到车场的缓存数据
             * 如果不为空，在数据上增加信息
             * */
            Object placeMap = objectList.get(2);

        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
