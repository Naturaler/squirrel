package com.yrx.squirrel.nut.rpc.client.api;

import com.yrx.squirrel.nut.rpc.client.proxy.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by r.x on 2020/7/29.
 */
@RestController
@RequestMapping("/demo")
public class DemoApi {
    @Autowired
    private ProxyService demoService;
    // @Autowired
    // private DemoService02 demoService02;

    @GetMapping("/proxy")
    public String proxy() {
        demoService.say("hello world");
        // demoService02.hello("hi world", 2);
        return "good bye";
    }
}
