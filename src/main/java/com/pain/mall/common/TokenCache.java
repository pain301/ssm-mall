package com.pain.mall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/6/9.
 */
public class TokenCache {
    private static final Logger logger = LoggerFactory.getLogger(TokenCache.class);
    private static final String TOKEN_PREFIX = "token_";

    private static LoadingCache<String, String> localCache = CacheBuilder
            .newBuilder()
            .initialCapacity(1000)
            .maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                // 缓存没有命中执行返回
                @Override
                public String load(String s) throws Exception {
                    return null;
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(TOKEN_PREFIX + key, value);
    }

    public static String getKey(String key) {
        String tokenValue = null;
        try {
            tokenValue = localCache.get(TOKEN_PREFIX + key);
        } catch (ExecutionException e) {
            logger.error("localCache get error",e);
        }

        return tokenValue;
    }

}