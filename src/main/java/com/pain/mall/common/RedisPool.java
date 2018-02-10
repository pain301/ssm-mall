package com.pain.mall.common;

import com.pain.mall.utils.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Administrator on 2018/2/6.
 */
public class RedisPool {

    /**
     * jedis 连接池
     */
    private static JedisPool jedisPool;

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

    private static void initRedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        // 连接耗尽后阻塞
        config.setBlockWhenExhausted(true);

        String ip = PropertiesUtil.getString("redis.ip");
        Integer port = PropertiesUtil.getInt("redis.port");

        jedisPool = new JedisPool(config, ip, port, 2 * 1000);
    }

    static {
        initRedisPool();
    }

    public static Jedis getResource() {
        return jedisPool.getResource();
    }

    public static void returnResource(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis) {
        jedisPool.returnBrokenResource(jedis);
    }

    // TODO test redis pool
}
