package com.pain;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by Administrator on 2017/8/23.
 */
public class TestBeanLifeCycle {

    public static void test_beanfactory() {
        Resource res = new ClassPathResource("com/pain/beans.xml");
        BeanFactory bf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) bf);
        beanDefinitionReader.loadBeanDefinitions(res);

        ((DefaultListableBeanFactory)bf).addBeanPostProcessor(new MyBeanPostProcessor());
        ((DefaultListableBeanFactory)bf).addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());

        Car car1 = (Car) bf.getBean("car");
        Car car2 = (Car) bf.getBean("car");

        car1.setColor("blue");
        System.out.println(car1 == car2);

        ((DefaultListableBeanFactory) bf).destroySingletons();
    }

    public static void test_applicationContext() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:com/pain/beans.xml");
        Car car1 = (Car) ctx.getBean("car");
        car1.setColor("red");
        Car car2 = (Car) ctx.getBean("car");
        System.out.println(car1 == car2);
    }

    public static void main(String[] args) {
        test_beanfactory();
        //test_applicationContext();
    }
}
