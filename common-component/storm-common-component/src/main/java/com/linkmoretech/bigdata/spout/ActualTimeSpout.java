package com.linkmoretech.bigdata.spout;

import com.linkmoretech.bigdata.constant.BoltCommonField;
import org.apache.commons.io.FileUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author: alec
 * Description: 数据输出数据源-输出实时数据
 * @date: 11:33 2019-07-15
 */
public class ActualTimeSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }
    /**
     * 数据生产，目前模拟从硬盘读取文件
     * */
    @Override
    public void nextTuple() {

        /**
         * 读取这个文件下的数据
         * */
        String url = "/Users/alec/data";
        String[] text = new String[] {"txt"};
        Collection<File> files =   FileUtils.listFiles(new File(url), text, true);
        /**
         * 便利读取的文件
         * */
        files.forEach(file -> {
            try {
                List<String> textValues =  FileUtils.readLines(file);
                textValues.forEach(value -> {
                    /**
                     * 将文件内容发射出去
                     * */
                    collector.emit(new Values(value));
                });
                /**
                 * 将文件重命名
                 * */
                FileUtils.moveFile(file, new File(file.getAbsolutePath() +System.currentTimeMillis()+ ".bak"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(BoltCommonField.keyResource));
    }
}
