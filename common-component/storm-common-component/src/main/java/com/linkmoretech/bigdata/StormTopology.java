package com.linkmoretech.bigdata;

import com.linkmoretech.bigdata.bolt.FiltrationFixedMapper;
import com.linkmoretech.bigdata.bolt.GroupSummaryBolt;
import com.linkmoretech.bigdata.bolt.ParsingDataBolt;
import com.linkmoretech.bigdata.bolt.PersistenceMapper;
import com.linkmoretech.bigdata.spout.ActualTimeSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.redis.bolt.RedisLookupBolt;
import org.apache.storm.redis.bolt.RedisStoreBolt;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.topology.TopologyBuilder;

/**
 * @Author: alec
 * Description:
 * @date: 11:39 2019-07-15
 */
public class StormTopology {

    public static void main(String[] args) {
        String host = "192.168.1.218";
        Integer port = 6380;
        String password = "ppyx";

        JedisPoolConfig poolConfig = new JedisPoolConfig.Builder()
                .setHost(host).setPort(port).setDatabase(3).setPassword(password).build();

        /**
         * 配置计算输出
         * */
        ActualTimeSpout actualTimeSpout = new ActualTimeSpout();

        /**
         * 定义第一个解析元数据对bolt
         * */
        ParsingDataBolt parsingDataBolt = new ParsingDataBolt();

        /**
         * 定义第二个从redis 中检查是否存在对长租车牌对bolt
         * */

        FiltrationFixedMapper filtrationFixedMapper = new FiltrationFixedMapper();
        RedisLookupBolt filtrationFixedBolt = new RedisLookupBolt(poolConfig, filtrationFixedMapper);


        /**
         * 定义第三个 汇总各车场出入对车辆数
         * */
        GroupSummaryBolt groupSummaryBolt = new GroupSummaryBolt();

        /**
         * 定义第四个 持久化数据
         * */
        PersistenceMapper persistenceMapper = new PersistenceMapper();
        RedisStoreBolt redisStoreBolt = new RedisStoreBolt(poolConfig, persistenceMapper);

        TopologyBuilder topologyBuilder = new TopologyBuilder();
        /**
         * 配置计算拓扑
         * */
        topologyBuilder.setSpout("actualTimeSpout", actualTimeSpout);
        topologyBuilder.setBolt("parsingDataBolt", parsingDataBolt).shuffleGrouping("actualTimeSpout");
        topologyBuilder.setBolt("filtrationFixedBolt", filtrationFixedBolt).shuffleGrouping("parsingDataBolt");
        topologyBuilder.setBolt("groupSummaryBolt", groupSummaryBolt).shuffleGrouping("filtrationFixedBolt");
        topologyBuilder.setBolt("redisStoreBolt", redisStoreBolt).shuffleGrouping("groupSummaryBolt");
        /**
         * 定义本地模拟运行环境
         * */
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("stormTopology", new Config(), topologyBuilder.createTopology());
    }
}
