package com.config.service;

import com.config.dao.LoginDao;
import com.config.dao.UserDao;

/**
 * Created by Administrator on 2017/8/26.
 */
public class LoginService {
    private UserDao userDao;
    private LoginDao loginDao;

    public LoginService() {
        System.out.println("LoginService construct...");
    }

    public void service() {
        userDao.check();
        loginDao.login();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public LoginDao getLoginDao() {
        return loginDao;
    }

    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }
}
