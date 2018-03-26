package com.pain.mall.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pain.mall.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/8.
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 序列化所有字段
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

        // 不序列化空字段
        // objectMapper.setSerializationInclusion(Inclusion.NON_NULL);

        // 取消默认转换 timestamps
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 忽略空 Bean 转 json 的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        // 忽略 json 字符串中存在而对象中不存在的错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 所有日期同一格式：yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
    }

    public static <T> String objToStr(T obj) {
        if (null == obj) {
            return null;
        }

        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.warn("Parse Object to String error: ", e);
            return null;
        }
    }

    public static <T> String objToPrettyStr(T obj) {
        if (null == obj) {
            return null;
        }

        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            logger.warn("Parse Object to Pretty String error: ", e);
            return null;
        }
    }

    public static <T> T strToObj(String value, Class<T> clazz) {
        if (StringUtils.isBlank(value) || null == clazz) {
            return null;
        }

        try {
            return clazz.equals(String.class) ? (T) value : objectMapper.readValue(value, clazz);
        } catch (IOException e) {
            logger.warn("Parse str to Object error: ", e);
            return null;
        }
    }

    public static <T> T strToObj(String value, TypeReference<T> type) {
        if (StringUtils.isBlank(value) || null == type) {
            return null;
        }

        try {
            return type.getType().equals(String.class) ? (T) value : objectMapper.readValue(value, type);
        } catch (IOException e) {
            logger.warn("Parse str to Object error: ", e);
            return null;
        }
    }

    public static <T> T strToObj(String value, Class<?> collectionClazz, Class<?>... elementClazz) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazz);

        try {
            return objectMapper.readValue(value, javaType);
        } catch (IOException e) {
            logger.warn("Parse str to Object error: ", e);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(strToObj("4", int.class));
    }

    public static void main1(String[] args) {
        User user = new User();
        user.setId(1);
        user.setUsername("jack");

        System.out.println("user: " + objToStr(user));
        System.out.println("user: " + objToPrettyStr(user));

        User tmpUser = strToObj(objToStr(user), User.class);

        List<User> list = Lists.newArrayList();
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("page");
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("pain");
        list.add(user1);
        list.add(user2);

        System.out.println("======================");
        String listStr = objToPrettyStr(list);
        System.out.println(listStr);
        System.out.println("======================");

        Map<String, User> map = Maps.newHashMap();
        map.put("user1", user1);
        map.put("user2", user2);

        String mapStr = objToPrettyStr(map);
        System.out.println(mapStr);
        System.out.println("======================");
        System.out.println("======================");

        List<User> users = strToObj(listStr, new TypeReference<List<User>>() {});
        System.out.println(users);
        System.out.println("======================");

        Map<String, User> userMap = strToObj(mapStr, new TypeReference<Map<String, User>>() {});
        System.out.println(userMap);
        System.out.println("end");

        System.out.println(strToObj("name", new TypeReference<String>() {}));

        users = strToObj(listStr, List.class, User.class);
        System.out.println(users);

        userMap = strToObj(mapStr, Map.class, String.class, User.class);
        System.out.println(userMap);

        System.out.println(strToObj("name", String.class));
        System.out.println("end");
    }
}
