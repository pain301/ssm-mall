package com.pain.mall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by Administrator on 2017/6/8.
 */
public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String USERNAME = "username";

    public static final String EMAIL = "email";

    public static final String TOKEN_PREFIX = "token_";

    public interface RedisCacheTime {
        int REDIS_SESSION_TIME = 30 * 60;
    }

    public interface Role {
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }

    public interface ProductOrderBy {
        Set<String> PRICE_ORDER = Sets.newHashSet("price_desc", "price_asc");
    }

    public interface Cart {
        int CHECKED = 1;
        int UN_CHECKED = 0;

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public enum ProductStatus {
        ON_SALE("在线", 1);
        private String value;
        private int code;

        ProductStatus(String value, int code) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public enum OrderStatus {
        CANCELED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已支付"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSED(60, "订单关闭");

        private int code;
        private String value;

        public static OrderStatus codeOf(int code) {
            for (OrderStatus orderStatus : OrderStatus.values()) {
                if (code == orderStatus.getCode()) {
                    return orderStatus;
                }
            }
            throw new RuntimeException("没有对应的订单类型");
        }

        OrderStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum PayPlatform {
        ALIPAY(1, "支付宝");

        private int code;
        private String value;

        PayPlatform(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public enum PaymentType {
        ONLINE_PYA(1, "在线支付");

        private int code;
        private String value;

        public static PaymentType codeOf(int code) {
            for (PaymentType paymentType : values()) {
                if (code == paymentType.getCode()) {
                    return paymentType;
                }
            }
            throw new RuntimeException("没有对应的支付类型");
        }

        PaymentType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public interface AlipayCallback {
        String WAIT_BUYER_PAY_STATUS = "WAIT_BUYER_PAY";
        String TRADE_SUCCESS_STATUS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }
}
