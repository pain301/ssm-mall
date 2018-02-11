package com.pain.mall.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/2/10.
 */
public class CookieUtil {
    private static final String COOKIE_DOMAIN = "painmall.com";
    private static final String COOKIE_NAME = "login_token";

    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);

        // 设置在根目录
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        // 若不设置则只存在于内存中，不会写入硬盘，-1 表示永久
        cookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(cookie);
        logger.info("Write cookie name: {}, cookie value: {}", cookie.getName(), cookie.getValue());
    }

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    logger.info("Read cookie name: {}, cookie value: {}", cookie.getName(), cookie.getValue());
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public static void deleteLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    logger.info("Delete cookie name: {}, cookie value: {}", cookie.getName(), cookie.getValue());
                    return ;
                }
            }
        }
    }
}
