package com.pain.mall.utils;

import com.pain.mall.common.RedisPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by Administrator on 2018/2/7.
 */
public class RedisPoolUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisPoolUtil.class);

    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            logger.error("redis set key: {}, value: {}, error: ", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }

        RedisPool.returnResource(jedis);
        return result;
    }

    public static String get(String key) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            logger.error("redis get key: {}, error: ", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }

        RedisPool.returnResource(jedis);
        return result;
    }

    public static String setEx(String key, String value, int seconds) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getResource();
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error("redis setEx key: {}, value: {}, error: ", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }

        RedisPool.returnResource(jedis);
        return result;
    }

    public static Long delete(String key) {
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getResource();
            result = jedis.del(key);
        } catch (Exception e) {
            logger.error("redis delete key: {}, error: ", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }

        RedisPool.returnResource(jedis);
        return result;
    }

    public static Long expire(String key, int seconds) {
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getResource();
            jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error("redis expire key: {}, ", key);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }

        RedisPool.returnResource(jedis);
        return result;
    }
}
