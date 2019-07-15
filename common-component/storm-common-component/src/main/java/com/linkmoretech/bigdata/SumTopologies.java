package com.linkmoretech.bigdata;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * @Author: alec
 * Description: 求和Storm拓扑
 * @date: 10:17 2019-07-12
 */
public class SumTopologies {


    /**
     * 数据源，源源不断的来源数据
     * */
    public static class DataSpout extends BaseRichSpout {

        SpoutOutputCollector collector;

        /**
         * 初始化配置
         * */
        @Override
        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
            this.collector = collector;
        }
        int num = 0;
        /**
         * 核心，用于产生数据，并将数据发射出去
         *
         * */
        @Override
        public void nextTuple() {
            collector.emit(new Values(++num));
            System.out.println("接受到流数据 " + num);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 定义发射出去数据到字段名
         * */
        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("num"));
        }
    }


    /**
     * 数据处理单元
     * */
    public static class SumBolt extends BaseRichBolt {

        /**
         * 准备工作，只执行一次
         * */
        @Override
        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
            System.out.println("bolt 准备工作，只执行一次");
        }
        int sum = 0;
        /**
         * 重点，逻辑执行单元
         * */
        @Override
        public void execute(Tuple input) {

           sum += input.getIntegerByField("num");
           System.out.println("求和结果 " + sum);
        }
        /**
         * 需要向下一个bolt发送数据的字段定义
         * */
        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {

        }
    }

    /**
     * storm 拓扑
     * 组装
     * */
    public static void main(String[] args) {

        LocalCluster localCluster = new LocalCluster();

        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("DataSpout", new DataSpout());
        topologyBuilder.setBolt("SumBolt", new SumBolt()).shuffleGrouping("DataSpout");

        /**
         *  名称
         *  配置
         *  build
         * */
        localCluster.submitTopology("sumTopologies", new Config(), topologyBuilder.createTopology());


    }
}
