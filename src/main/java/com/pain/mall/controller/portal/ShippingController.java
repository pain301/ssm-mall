package com.pain.mall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.pain.mall.common.Const;
import com.pain.mall.common.ResponseCode;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.Shipping;
import com.pain.mall.pojo.User;
import com.pain.mall.service.IShippingService;
import com.pain.mall.utils.CookieUtil;
import com.pain.mall.utils.JsonUtil;
import com.pain.mall.utils.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/6/17.
 */
@Controller
@RequestMapping(value = "/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    @RequestMapping(value = "add", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse add(HttpServletRequest request, Shipping shipping) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.add(user.getId(), shipping);
    }

    @RequestMapping(value = "delete", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse delete(HttpServletRequest request, Integer shippingId) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.delete(user.getId(), shippingId);
    }

    @RequestMapping(value = "update", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse update(HttpServletRequest request, Shipping shipping) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.update(user.getId(), shipping);
    }

    @RequestMapping(value = "select", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<Shipping> select(HttpServletRequest request, Integer shippingId) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.select(user.getId(), shippingId);
    }

    @RequestMapping(value = "list", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                         HttpServletRequest request) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.list(user.getId(), pageNum, pageSize);
    }
}
