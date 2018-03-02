package com.pain.mall.common;

import com.pain.mall.utils.PropertiesUtil;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2018/2/28.
 */
@Component
public class RedissonManager {

    private static final Logger logger = LoggerFactory.getLogger(RedissonManager.class);

    private Config config = new Config();

    private Redisson redisson = null;

    @PostConstruct
    private void init() {
        String ip = PropertiesUtil.getString("redis.ip");
        Integer port = PropertiesUtil.getInt("redis.port");

        // redissson 暂不支持一致性算法
        try {
            config.useSingleServer().setAddress(new StringBuilder().append(ip).append(":").append(port).toString());

            redisson = (Redisson) Redisson.create(config);
            logger.info("初始化 redisson 成功");
        } catch (Exception e) {
            logger.error("初始 redisson 失败, error: ", e);
            e.printStackTrace();
        }
    }

    public Redisson getRedisson() {
        return redisson;
    }
}
