package com.pain.mall.controller.portal;

import com.pain.mall.common.Const;
import com.pain.mall.common.ResponseCode;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.User;
import com.pain.mall.service.ICartService;
import com.pain.mall.utils.CookieUtil;
import com.pain.mall.utils.JsonUtil;
import com.pain.mall.utils.RedisPoolUtil;
import com.pain.mall.vo.CartVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/6/17.
 */
@Controller
@RequestMapping(value = "/cart/")
public class CartController {

    @Autowired
    private ICartService cartService;

    @RequestMapping(value = "add", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<CartVo> add(HttpServletRequest request, Integer productId, Integer count) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.add(user.getId(), productId, count);
    }

    @RequestMapping(value = "update", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<CartVo> update(HttpServletRequest request, Integer productId, Integer count) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.update(user.getId(), productId, count);
    }

    @RequestMapping(value = "delete", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<CartVo> delete(HttpServletRequest request, String productIds) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.delete(user.getId(), productIds);
    }

    @RequestMapping(value = "list", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<CartVo> list(HttpServletRequest request) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.list(user.getId());
    }

    @RequestMapping(value = "select_all", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<CartVo> selectAll(HttpServletRequest request) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    @RequestMapping(value = "un_select_all", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<CartVo> unSelectAll(HttpServletRequest request) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    @RequestMapping(value = "select", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<CartVo> select(HttpServletRequest request, Integer productId) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    @RequestMapping(value = "un_select", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<CartVo> unSelect(HttpServletRequest request, Integer productId) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }

    @RequestMapping(value = "cart_product_count", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<Integer> cartProductCount(HttpServletRequest request) {
        String token = CookieUtil.readLoginToken(request);

        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        String value = RedisPoolUtil.get(token);
        User user = JsonUtil.strToObj(value, User.class);

        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.getCartProductCount(user.getId());
    }
}
