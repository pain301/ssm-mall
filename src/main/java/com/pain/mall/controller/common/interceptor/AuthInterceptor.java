package com.pain.mall.controller.common.interceptor;

import com.google.common.collect.Maps;
import com.pain.mall.common.Const;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.User;
import com.pain.mall.utils.CookieUtil;
import com.pain.mall.utils.JsonUtil;
import com.pain.mall.utils.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/23.
 */
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("preHandle");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();

        StringBuffer paramBuf = new StringBuffer();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> entry = iterator.next();
            String key = entry.getKey();
            String[] value = entry.getValue();
            paramBuf.append(key).append('=').append(Arrays.toString(value));
        }

        // 避免拦截器拦截导致无法登录
        if (StringUtils.equals(className, "UserManageController") && StringUtils.equals(methodName, "login")) {
            logger.info("权限拦截器拦截请求，className: {}, methodName: {}", className, methodName);
            return true;
        }

        logger.info("权限拦截器拦截请求，className: {}, methodName: {}, param: {}", className, methodName, paramBuf.toString());

        User user = null;
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isNotBlank(token)) {
            String value = RedisPoolUtil.get(token);
            user = JsonUtil.strToObj(value, User.class);
        }

        // 返回 false 不会进入 controller 方法中
        if (null == user || (user.getRole() != Const.Role.ROLE_ADMIN)) {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");

            PrintWriter out = response.getWriter();

            if (null == user) {
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "uploadRichText")) {
                    Map map = Maps.newHashMap();
                    map.put("success", false);
                    map.put("msg", "用户未登录");
                    out.print(JsonUtil.objToStr(map));
                } else {
                    out.print(JsonUtil.objToStr(ServerResponse.createByErrorMsg("用户未登录")));
                }
            } else {
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "uploadRichText")) {
                    Map map = Maps.newHashMap();
                    map.put("success", false);
                    map.put("msg", "无权限操作");
                    out.print(JsonUtil.objToStr(map));
                } else {
                    out.print(JsonUtil.objToStr(ServerResponse.createByErrorMsg("用户无权限操作")));
                }
            }

            out.flush();
            out.close();

            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("afterCompletion");
    }
}
