package com.pain;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * Created by Administrator on 2017/8/23.
 */
public class Car implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {
    private String brand;
    private String color;
    private int maxSpeed;

    private BeanFactory beanFactory;
    private String beanName;

    Car() {
        System.out.println("Car#Car()");
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        System.out.println("Car#setBrand(), brand: " + brand);
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", maxSpeed=" + maxSpeed +
                '}';
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("Car#setBeanFactory() BeanFactoryAware");
    }

    public void setBeanName(String s) {
        this.beanName = s;
        System.out.println("Car#setBeanName() BeanNameAware");
    }

    public void destroy() throws Exception {
        System.out.println("Car#destroy() DisposableBean");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("Car#afterPropertiesSet() InitializingBean");
    }

    public void car_init() {
        System.out.println("Car#car_init()");
    }

    public void car_destroy() {
        System.out.println("Car#car_destroy()");
    }
}
