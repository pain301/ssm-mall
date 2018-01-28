package com.config;

import com.config.dao.DaoConfig;
import com.config.service.LoginService;
import com.config.service.ServiceConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Administrator on 2017/8/26.
 */
public class TestConfig {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(DaoConfig.class);
        ctx.register(ServiceConfig.class);

        ctx.refresh();
        LoginService loginService = ctx.getBean(LoginService.class);
        loginService.service();
    }
}
