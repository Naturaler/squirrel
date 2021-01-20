package com.yrx.squirrel.nut.spring.mock;

import com.yrx.squirrel.nut.spring.handler.Handler;
import com.yrx.squirrel.nut.spring.handler.HandlerFactory;
import com.yrx.squirrel.nut.spring.handler.IHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by r.x on 2020/10/4.
 */
@Slf4j
@Component
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("bean factory: {}", beanFactory);
        // // 可以直接删除某个 beandefinition
        // ((DefaultListableBeanFactory) beanFactory).removeBeanDefinition("mockDao");
        HandlerFactory handlerFactory = beanFactory.getBean(HandlerFactory.class);
        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(Handler.class);
        String[] beanNamesForAnnotation = beanFactory.getBeanNamesForAnnotation(Handler.class);
        for (String s : beanNamesForAnnotation) {
            handlerFactory.register(s, (IHandler) beanFactory.getBean(s));
        }
    }
}
