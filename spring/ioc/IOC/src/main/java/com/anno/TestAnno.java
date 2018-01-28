package com.anno;

import com.anno.service.LoginService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/8/26.
 */
public class TestAnno {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("com/anno/beans.xml");
        LoginService loginService = ctx.getBean(LoginService.class);
        loginService.service();
    }
}
