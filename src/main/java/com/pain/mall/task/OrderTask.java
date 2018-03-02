package com.pain.mall.task;

import com.pain.mall.common.Const;
import com.pain.mall.common.RedissonManager;
import com.pain.mall.service.IOrderService;
import com.pain.mall.utils.PropertiesUtil;
import com.pain.mall.utils.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/2/25.
 */

@Component
public class OrderTask {
    private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedissonManager redissonManager;

    @PreDestroy
    public void delOrderCloseLock() {
        RedisPoolUtil.delete(Const.RedisLock.ORDER_CLOSE_TASK_LOCK);
    }

    // @Scheduled(cron = "* */1 * * * ?")
    public void closeOrder1() {
        logger.info("开始关闭订单任务");
        Integer hours = PropertiesUtil.getInt("order.close.task.time.hours", 2);
        orderService.closeOrder(hours);
    }

    // @Scheduled(cron = "* */1 * * * ?")
    public void closeOrderV2() {
        logger.info("开始关闭订单任务");
        Integer lockTime = PropertiesUtil.getInt("order.close.lock.time", 5000);
        Long result = RedisPoolUtil.setNx(Const.RedisLock.ORDER_CLOSE_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTime));

        if (null != result && 1 == result.intValue()) {
            closeOrder();
        } else {
            logger.info("获取分布式锁失败");
        }
    }

    // @Scheduled(cron = "* */1 * * * ?")
    public void closeOrderV3() {
        logger.info("开始关闭订单任务");
        Integer lockTime = PropertiesUtil.getInt("order.close.lock.time", 5000);
        Long result = RedisPoolUtil.setNx(Const.RedisLock.ORDER_CLOSE_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTime));

        if (null != result && 1 == result.intValue()) {
            closeOrder();
        } else {
            String value1 = RedisPoolUtil.get(Const.RedisLock.ORDER_CLOSE_TASK_LOCK);
            if (null != value1 && System.currentTimeMillis() > Long.valueOf(value1)) {
                String value2 = RedisPoolUtil.getSet(Const.RedisLock.ORDER_CLOSE_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTime));

                if (null == value2 || StringUtils.equals(value1, value2)) {
                    closeOrder();
                } else {
                    logger.info("获取分布式锁失败");
                }
            } else {
                logger.info("获取分布式锁失败");
            }
        }
    }

    @Scheduled(cron = "* */1 * * * ?")
    public void closeOrderV4() {
        logger.info("开始关闭订单任务");
        RLock lock = redissonManager.getRedisson().getLock(Const.RedisLock.ORDER_CLOSE_TASK_LOCK);

        boolean flag = false;
        try {
            // 获取锁时不进行等待
            if (flag = lock.tryLock(0, PropertiesUtil.getInt("order.close.lock.time", 5000), TimeUnit.MILLISECONDS)) {
                logger.info("获取锁 {}, threadName: {}", Const.RedisLock.ORDER_CLOSE_TASK_LOCK, Thread.currentThread().getName());
                Integer hours = PropertiesUtil.getInt("order.close.task.time.hours", 2);
                orderService.closeOrder(hours);
            } else {
                logger.info("获取锁 {} 失败, threadName: {}", Const.RedisLock.ORDER_CLOSE_TASK_LOCK, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            logger.error("获取锁 {} 异常，threadName: {}, error: ", Const.RedisLock.ORDER_CLOSE_TASK_LOCK, Thread.currentThread().getName(), e);
            e.printStackTrace();
        } finally {
            if (flag) {
                lock.unlock();
                logger.info("释放锁 {}, threadName: {}", Const.RedisLock.ORDER_CLOSE_TASK_LOCK, Thread.currentThread().getName());
            }
        }
    }

    private void closeOrder() {
        RedisPoolUtil.expire(Const.RedisLock.ORDER_CLOSE_TASK_LOCK,
                PropertiesUtil.getInt("order.close.lock.time", 5000));
        logger.info("获取锁 {}, threadName: {}", Const.RedisLock.ORDER_CLOSE_TASK_LOCK, Thread.currentThread().getName());
        Integer hours = PropertiesUtil.getInt("order.close.task.time.hours", 2);
        orderService.closeOrder(hours);
        RedisPoolUtil.delete(Const.RedisLock.ORDER_CLOSE_TASK_LOCK);
        logger.info("释放锁 {}, threadName: {}", Const.RedisLock.ORDER_CLOSE_TASK_LOCK, Thread.currentThread().getName());
    }

}