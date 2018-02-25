package com.pain.mall.controller.backend;

import com.pain.mall.common.Const;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.User;
import com.pain.mall.service.IUserService;
import com.pain.mall.utils.CookieUtil;
import com.pain.mall.utils.JsonUtil;
import com.pain.mall.utils.RedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/6/11.
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse res) {
        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();

            if (Const.Role.ROLE_ADMIN == user.getRole()) {
                CookieUtil.writeLoginToken(res, session.getId());
                RedisPoolUtil.setEx(session.getId(), JsonUtil.objToStr(response.getData()), Const.RedisCacheTime.REDIS_SESSION_TIME);
                return response;
            } else {
                return ServerResponse.createByErrorMsg("不是管理员，无法登录");
            }
        } else {
            return response;
        }
    }
}
