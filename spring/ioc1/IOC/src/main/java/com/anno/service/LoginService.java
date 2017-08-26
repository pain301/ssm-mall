package com.anno.service;

import com.anno.dao.LoginDao;
import com.anno.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/26.
 */

@Service
public class LoginService {
    @Autowired
    private LoginDao loginDao;

    @Autowired
    private UserDao userDao;

    LoginService() {
        System.out.println("LoginService construct...");
    }

    public void service() {
        userDao.check();
        loginDao.login();
    }
}
