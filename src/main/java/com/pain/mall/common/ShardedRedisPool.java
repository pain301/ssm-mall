package com.pain.mall.common;

import com.pain.mall.utils.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/13.
 */
public class ShardedRedisPool {

    /**
     * jedis 连接池
     */
    private static ShardedJedisPool shardedJedisPool;

    /**
     * jedis 最大连接数
     */
    private static Integer maxTotal = PropertiesUtil.getInt("redis.max.total", 20);

    /**
     * jedis 连接最大空闲数
     */
    private static Integer maxIdle = PropertiesUtil.getInt("redis.max.idle", 10);

    /**
     * jedis 连接最小空闲数
     */
    private static Integer minIdle = PropertiesUtil.getInt("redis.min.idle", 2);

    /**
     * brorrow 连接时是否进行验证
     */
    private static Boolean testOnBorrow = PropertiesUtil.getBool("redis.test.borrow", true);

    /**
     * return 连接时是否进行验证
     */
    private static Boolean testOnReturn = PropertiesUtil.getBool("redis.test.return", true);

    private static void initShardedRedisPool() {

        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        // 连接耗尽后阻塞
        config.setBlockWhenExhausted(true);

        String ip1 = PropertiesUtil.getString("redis1.ip");
        Integer port1 = PropertiesUtil.getInt("redis1.port");

        String ip2 = PropertiesUtil.getString("redis2.ip");
        Integer port2 = PropertiesUtil.getInt("redis2.port");

        JedisShardInfo info1 = new JedisShardInfo(ip1, port1, 2 * 1000);

        // 密码
        // info1.setPassword("123456");
        JedisShardInfo info2 = new JedisShardInfo(ip2, port2, 2 * 1000);
        List<JedisShardInfo> infoList = new ArrayList<>(2);
        infoList.add(info1);
        infoList.add(info2);

        shardedJedisPool = new ShardedJedisPool(config, infoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static {
        initShardedRedisPool();
    }

    public static ShardedJedis getResource() {
        return shardedJedisPool.getResource();
    }

    public static void returnResource(ShardedJedis jedis) {
        shardedJedisPool.returnResource(jedis);
    }

    public static void returnBrokenResource(ShardedJedis jedis) {
        shardedJedisPool.returnBrokenResource(jedis);
    }

}
