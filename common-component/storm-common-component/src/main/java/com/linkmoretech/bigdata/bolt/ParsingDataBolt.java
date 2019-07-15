package com.linkmoretech.bigdata.bolt;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.bigdata.constant.BoltCommonField;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * @Author: alec
 * Description: 实时统计车场出入记录的第一个计算单元
 * 用于将读取的数据解析成 车牌，时间， 车场，类型 四个字段
 * @date: 10:54 2019-07-15
 */
public class ParsingDataBolt extends BaseRichBolt {

    private OutputCollector collector;

    /**
     * 需要将解析的数据推向下一个计算单元，因此需要配置弹射
     * */
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        /*定义输出源输出的字段*/

        String record = input.getStringByField(BoltCommonField.keyResource);
        /**
         * 对记录进行解析
         * */
        JSONObject jsonObject = JSONObject.parseObject(record);
        String recordPark = jsonObject.getString(BoltCommonField.keyParkCode);
        Integer direction = jsonObject.getInteger(BoltCommonField.keyDirection);
        String carNo = jsonObject.getString(BoltCommonField.keyCarNo);
        /**
         * 将数据发送出去
         * */
        System.out.println("bolt1 发射数据 " + carNo + ", " + direction + ", " + recordPark );
        collector.emit(new Values(recordPark, direction, carNo));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(BoltCommonField.keyParkCode, BoltCommonField.keyDirection,
                BoltCommonField.keyCarNo));
    }
}
