package com.linkmoretech.bigdata.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.List;
import java.util.Map;

/**
 * @Author: alec
 * Description: 车辆计数solt ,计算好后交友下一个solt 做落地
 * @date: 18:27 2019-07-12
 */
public class RecordCountBolt extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {

      List<Object> objectList =   input.getValues();
      if(objectList != null && objectList.size() == 2) {
          /**
           * 解析参数
           * */
          String source = String.valueOf(objectList.get(0)).split(":")[0];
          int count = 0;
          Object renterCount = objectList.get(1);
          if (renterCount != null) {
              count = 1;
          }
          /**
           * 将采集编号 和 数 交由下一个bolt处理
           * */
          collector.emit(new Values(source, count));

      }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("source", "count"));
    }
}
