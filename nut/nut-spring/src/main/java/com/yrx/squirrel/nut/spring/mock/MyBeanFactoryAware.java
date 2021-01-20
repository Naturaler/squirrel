package com.yrx.squirrel.nut.spring.mock;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * Created by r.x on 2020/10/20.
 */
@Component
public class MyBeanFactoryAware implements ResourceLoaderAware {
    private ResourceLoader beanFactory;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.beanFactory = resourceLoader;

    }
}
