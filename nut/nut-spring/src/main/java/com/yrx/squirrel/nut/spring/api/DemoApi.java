package com.yrx.squirrel.nut.spring.api;

import com.yrx.squirrel.nut.spring.handler.HandlerFactory;
import com.yrx.squirrel.nut.spring.handler.IHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by r.x on 2020/10/20.
 */
@RestController
@RequestMapping("/demo")
public class DemoApi {
    @Autowired
    private HandlerFactory handlerFactory;

    @GetMapping("/list/handler")
    public String listHandler() {
        Map<String, IHandler> map = handlerFactory.listAll();
        return map.toString();
    }
}
