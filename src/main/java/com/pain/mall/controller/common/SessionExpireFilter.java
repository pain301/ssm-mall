package com.pain.mall.controller.common;

import com.pain.mall.common.Const;
import com.pain.mall.pojo.User;
import com.pain.mall.utils.CookieUtil;
import com.pain.mall.utils.JsonUtil;
import com.pain.mall.utils.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2018/2/11.
 */
public class SessionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isNoneBlank(token)) {
            String value = RedisPoolUtil.get(token);
            User user = JsonUtil.strToObj(value, User.class);
            if (null != user) {
                RedisPoolUtil.expire(token, Const.RedisCacheTime.REDIS_SESSION_TIME);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
