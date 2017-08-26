package com.pain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/8/26.
 */
public class TestInject {

    public static void test_attr_inject() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("com/pain/beans1.xml");
        Phone phone1 = (Phone) ctx.getBean("phone1");
        Phone phone2 = (Phone) ctx.getBean("phone2");
        Phone phone3 = (Phone) ctx.getBean("phone3");
        Phone phone4 = (Phone) ctx.getBean("phone4");

        User user = (User) ctx.getBean("user");

        System.out.println(phone1);
        System.out.println(phone2);
        System.out.println(phone3);
        System.out.println(phone4);

        System.out.println(user);
    }

    public static void test_collection_attr_inject() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("com/pain/beans2.xml");
        Student student = (Student) ctx.getBean("student");
        System.out.println(student);
    }

    public static void main(String[] args) {
//        test_attr_inject();
        test_collection_attr_inject();
    }
}

class PhoneFactory1 {
    public Phone createPhone() {
        Phone phone = new Phone("huawei", 128);
        return phone;
    }
}

class PhoneFactory2 {
    public static Phone createPhone() {
        Phone phone = new Phone("oppo", 245);
        return phone;
    }
}