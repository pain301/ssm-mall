package com.pain.mall.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Administrator on 2017/6/15.
 */
public class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties properties;

    // TODO CHECK
    // 只加载执行一次
    static {
        String fileName = "mall.properties";
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(
                    PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件异常", e);
        }
    }

    public static String getString(String key) {

        if (StringUtils.isBlank(key)) {
            return null;
        }

        String value = properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getString(String key, String defaultValue) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        String value = properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }

    public static Integer getInt(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        String value = properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return Integer.valueOf(value);
    }

    public static Integer getInt(String key, Integer defaultValue) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        String value = properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }

        return Integer.valueOf(value);
    }

    public static Boolean getBool(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        String value = properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return Boolean.valueOf(value);
    }

    public static Boolean getBool(String key, Boolean defaultValue) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        String value = properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }

        return Boolean.valueOf(value);
    }

}
