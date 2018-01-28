package com.anno.dao;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/8/26.
 */
@Component
public class LoginDao {
    LoginDao() {
        System.out.println("LoginDao construct...");
    }

    public void login() {
        System.out.println("LoginDao login...");
    }
}
