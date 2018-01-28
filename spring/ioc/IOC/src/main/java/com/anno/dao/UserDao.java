package com.anno.dao;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/8/26.
 */
@Component
public class UserDao {
    UserDao() {
        System.out.println("UserDao construct...");
    }

    public void check() {
        System.out.println("UserDao check...");
    }
}
