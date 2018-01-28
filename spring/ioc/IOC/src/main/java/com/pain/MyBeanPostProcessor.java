package com.pain;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by Administrator on 2017/8/23.
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        if (s.equals("car")) {
            System.out.println("post process car before init by BeanPostProcessor");
        }
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (s.equals("car")) {
            System.out.println("post process car after init by BeanPostProcessor");
        }
        return o;
    }
}
