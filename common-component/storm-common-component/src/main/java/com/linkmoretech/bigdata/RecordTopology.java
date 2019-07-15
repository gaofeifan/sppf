package com.linkmoretech.bigdata;

import com.linkmoretech.bigdata.bolt.RecordCountBolt;
import com.linkmoretech.bigdata.bolt.RecordRedisGroupMapper;
import com.linkmoretech.bigdata.bolt.RecordRedisLockMapper;
import com.linkmoretech.bigdata.bolt.RecordSumBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.redis.bolt.RedisLookupBolt;
import org.apache.storm.redis.bolt.RedisStoreBolt;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.redis.common.mapper.RedisLookupMapper;
import org.apache.storm.redis.common.mapper.RedisStoreMapper;
import org.apache.storm.redis.trident.state.RedisState;
import org.apache.storm.topology.TopologyBuilder;

/**
 * @Author: alec
 * Description:
 * @date: 17:33 2019-07-12
 */
public class RecordTopology {

    public static void main(String[] args) {

        String host = "192.168.1.218";
        Integer port = 6380;
        String password = "ppyx";

        JedisPoolConfig poolConfig = new JedisPoolConfig.Builder()
                .setHost(host).setPort(port).setDatabase(3).setPassword(password).build();

        RedisState.Factory factory = new RedisState.Factory(poolConfig);

        /**
         * 校验是否是长租车牌的计算（基于redis）
         * */
        RecordRedisLockMapper recordRedisLockMapper = new RecordRedisLockMapper();
        RedisLookupBolt redisLookupBolt = new RedisLookupBolt(poolConfig, recordRedisLockMapper);

        /**
         * 换算其车场标记
         * */
        RecordRedisGroupMapper recordRedisGroupMapper = new RecordRedisGroupMapper();
        RedisLookupBolt redisLookupBolt1 = new RedisLookupBolt(poolConfig, recordRedisGroupMapper);




        LocalRecordSpout localRecordSpout = new LocalRecordSpout();

        LocalRecordGroupBolt localRecordGroupBolt = new LocalRecordGroupBolt();

        RecordCountBolt recordCountBolt = new RecordCountBolt();

        RecordSumBolt recordSumBolt = new RecordSumBolt();

        LocalCluster localCluster = new LocalCluster();

        TopologyBuilder topologyBuilder = new TopologyBuilder();

        topologyBuilder.setSpout("localRecordSpout", localRecordSpout);
        topologyBuilder.setBolt("localRecordGroupBolt", localRecordGroupBolt).shuffleGrouping("localRecordSpout");
        topologyBuilder.setBolt("redisLookupBolt", redisLookupBolt).shuffleGrouping("localRecordGroupBolt");
        topologyBuilder.setBolt("recordCountBolt", recordCountBolt).shuffleGrouping("redisLookupBolt");
        topologyBuilder.setBolt("recordCountBolt1", redisLookupBolt1).shuffleGrouping("recordCountBolt");
        topologyBuilder.setBolt("recordSumBolt", recordSumBolt).shuffleGrouping("recordCountBolt1");

        localCluster.submitTopology("RecordTopology", new Config(), topologyBuilder.createTopology());
    }
}
