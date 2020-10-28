package com.yrx.squirrel.nut.spring.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by r.x on 2020/10/4.
 */
@Slf4j
// @Component
public class CustomBeanPostProcess implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("before bean name: {}", beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("after bean name: {}", beanName);
        return bean;
    }
}
