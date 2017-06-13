package com.pain.mall.service;

import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.User;

/**
 * Created by Administrator on 2017/6/7.
 */
public interface IUserService {
    public ServerResponse<User> login(String username, String password);

    public ServerResponse<String> register(User user);

    public ServerResponse<String> checkValid(String tag, String type);

    public ServerResponse<String> selectQuestion(String username);

    public ServerResponse<String> checkAnswer(String username, String question, String answer);

    public ServerResponse<String> forgetResetPassword(String username, String password, String forgetToken);

    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    public ServerResponse<User> updateInformation(User user);

    public ServerResponse<User> getInformation(Integer userId);

    public ServerResponse<String> checkAdminRole(User user);
}
