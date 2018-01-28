package com.pain.mall.controller.portal;

import com.pain.mall.common.Const;
import com.pain.mall.common.ResponseCode;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.User;
import com.pain.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/6/7.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "login", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse register(User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "/check_valid", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse checkValid(String type, String tag) {
        return userService.checkValid(type, tag);
    }

    @RequestMapping(value = "get_user_info", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null != user) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMsg("用户未登录");
    }

    @RequestMapping(value = "forget_get_question", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) {
        return userService.selectQuestion(username);
    }

    @RequestMapping(value = "forget_check_answer", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    @RequestMapping(value = "forget_reset_password", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse forgetResetPassword(String username, String password, String forgetToken) {
        return userService.forgetResetPassword(username, password, forgetToken);
    }

    @RequestMapping(value = "reset_password", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse resetPassword(String passwordOld, String passwordNew, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        return userService.resetPassword(passwordOld, passwordNew, user);
    }

    @RequestMapping(value = "update_information", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<User> updateInformation(HttpSession session, User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (null  == currentUser) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        // 防止越权
        user.setId(currentUser.getId());
        user.setRole(currentUser.getRole());

        // 不允许改变用户名
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateInformation(user);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "get_information", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<User> getInformation(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        return userService.getInformation(currentUser.getId());
    }

}
