package com.yrx.squirrel.nut.rpc.server;

import com.yrx.squirrel.nut.rpc.contract.IHelloWorld;

/**
 * Created by r.x on 2020/7/26.
 */
public class HelloWorldImpl implements IHelloWorld {
    @Override
    public String hello(String content) {
        return content.toUpperCase();
    }
}
