package com.config.service;

import com.config.dao.DaoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/8/26.
 */
@Configuration
public class ServiceConfig {

    @Autowired
    private DaoConfig daoConfig;

    @Bean
    public LoginService loginService() {
        LoginService loginService = new LoginService();
        loginService.setUserDao(daoConfig.userDao());
        loginService.setLoginDao(daoConfig.loginDao());
        return loginService;
    }
}
