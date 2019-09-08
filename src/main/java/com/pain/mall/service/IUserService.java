package com.pain.mall.service;

import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.User;

/**
 * Created by Administrator on 2017/6/7.
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse register(User user);

    ServerResponse checkValid(String tag, String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse forgetResetPassword(String username, String password, String forgetToken);

    ServerResponse resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse<String> checkAdminRole(User user);
}
