package com.yrx.squirrel.nut.spring.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by r.x on 2020/10/20.
 */
@Component
public class HandlerFactory {
    private Map<String, IHandler> instances = new HashMap<>();

    public boolean register(String name, IHandler handler) {
        instances.put(name, handler);
        return true;
    }

    public Map<String, IHandler> listAll() {
        return instances;
    }
}
