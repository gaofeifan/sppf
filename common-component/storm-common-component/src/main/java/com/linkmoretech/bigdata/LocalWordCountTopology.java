package com.linkmoretech.bigdata;

import org.apache.commons.io.FileUtils;
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

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author: alec
 * Description: 单词计数器
 * @date: 11:05 2019-07-12
 */
public class LocalWordCountTopology {


    public static class DataSourceSpout extends BaseRichSpout {

        private SpoutOutputCollector collector;

        @Override
        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void nextTuple() {
            Collection<File> files = FileUtils.listFiles(new File("/Users/alec/data"),new String[]{"txt"}, true);
            for (File file: files) {
                try {
                   List<String> values = FileUtils.readLines(file);
                   for (String v : values) {
                       collector.emit(new Values(v));
                   }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("line"));
        }
    }

    /**
     * 对数据进行分割
     * */
    public static class SplitBolt extends BaseRichBolt {

        private OutputCollector outputCollector;
        @Override
        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
            this.outputCollector = collector;
        }

        @Override
        public void execute(Tuple input) {

          String line =  input.getStringByField("line");

          String[] lines = line.split(",");
          for (String l : lines) {
              outputCollector.emit(new Values(l));
          }
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word"));
        }
    }


    /**
     * 对数据进行计算
     * */
    public static class CountBolt extends BaseRichBolt {

        Map<String, Integer> wordMap = new HashMap<>();

        @Override
        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

        }

        /**
         * 获取单词
         * 汇总
         * 输出
         * */
        @Override
        public void execute(Tuple input) {
            String word = input.getStringByField("word");
            Integer count = wordMap.get(word);
            if (count == null) {
                count = 0;
            } else {
                count ++;
            }
           wordMap.put(word, count);
           Set<Map.Entry<String, Integer>> entrySet = wordMap.entrySet();
           for (Map.Entry<String, Integer> entry: entrySet) {
               System.out.println("单词 " + entry.getKey() + "出现 "+ entry.getValue() + "次") ;
           }

        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {

        }
    }


    public static void main(String[] args) {

        LocalCluster localCluster = new LocalCluster();
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("DataSourceSpout", new DataSourceSpout());
        topologyBuilder.setBolt("SplitBolt", new SplitBolt()).shuffleGrouping("DataSourceSpout");
        topologyBuilder.setBolt("CountBolt", new CountBolt()).shuffleGrouping("SplitBolt");
        localCluster.submitTopology("DataSourceSpout", new Config(),topologyBuilder.createTopology());
    }
}
