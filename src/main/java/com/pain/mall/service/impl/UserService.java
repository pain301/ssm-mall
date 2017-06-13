package com.pain.mall.service.impl;

import com.pain.mall.common.Const;
import com.pain.mall.common.ResponseCode;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.common.TokenCache;
import com.pain.mall.mapper.UserMapper;
import com.pain.mall.pojo.User;
import com.pain.mall.service.IUserService;
import com.pain.mall.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Administrator on 2017/6/7.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int count = userMapper.checkUsername(username);
        if (0 == count) {
            return ServerResponse.createByErrorMsg("用户名不存在");
        }

        String MD5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectUser(username, MD5Password);
        if (null == user) {
            return ServerResponse.createByErrorMsg("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse<String> validResponse = checkValid(Const.USERNAME, user.getUsername());
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = checkValid(Const.EMAIL, user.getEmail());
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int count = userMapper.insert(user);
        if (0 == count) {
            return ServerResponse.createByErrorMsg("注册用户失败");
        }
        return ServerResponse.createBySuccessMsg("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String tag, String type) {
        if (StringUtils.isNoneBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                int count = userMapper.checkUsername(tag);
                if (0 < count) {
                    return ServerResponse.createByErrorMsg("用户名已注册");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int coount = userMapper.checkEmail(tag);
                if (0 < coount) {
                    return ServerResponse.createByErrorMsg("邮箱已注册");
                }
            }
        } else {
            return ServerResponse.createByErrorMsg("参数错误");
        }
        return ServerResponse.createBySuccessMsg("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse response = checkValid(username, Const.USERNAME);
        if (response.isSuccess()) {
            return ServerResponse.createByErrorMsg("用户名不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNoneBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMsg("找回密码问题为空");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int count = userMapper.checkAnswer(username, question, answer);
        if (0 < count) {
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMsg("问题答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String password, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMsg("参数错误");
        }
        ServerResponse<String> response = checkValid(Const.USERNAME, username);
        if (response.isSuccess()) {
            return ServerResponse.createByErrorMsg("用户名不存在");
        }
        String token = TokenCache.getKey(username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMsg("Token 过期或失效");
        }
        if (StringUtils.equals(token, forgetToken)) {
            String md5Password = MD5Util.MD5EncodeUtf8(password);
            int count = userMapper.updatePasswordByUsername(username, md5Password);
            if (count <= 0) {
                return ServerResponse.createByErrorMsg("更新密码失败");
            } else {
                return ServerResponse.createBySuccessMsg("更新密码成功");
            }
        } else {
            return ServerResponse.createByErrorMsg("请重新获取 Token");
        }
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        int count = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (0 >= count) {
            return ServerResponse.createByErrorMsg("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        count = userMapper.updateByPrimaryKeySelective(user);
        if (0 >= count) {
            return ServerResponse.createByErrorMsg("密码重置失败");
        }
        return ServerResponse.createBySuccessMsg("密码重置成功");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        int count = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (0 <= count) {
            return ServerResponse.createByErrorMsg("Email 已被占用");
        }

        User updateUser = new User();
        updateUser.setId(user.getId());;
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        count = userMapper.updateByPrimaryKeySelective(updateUser);
        if (0 <= count) {
            return ServerResponse.createByErrorMsg("更新信息失败");
        }
        return ServerResponse.createBySuccess("更新信息成功", updateUser);
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            return ServerResponse.createByErrorMsg("找不到用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse<String> checkAdminRole(User user) {
        if (null != user && user.getRole() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
    }
}
