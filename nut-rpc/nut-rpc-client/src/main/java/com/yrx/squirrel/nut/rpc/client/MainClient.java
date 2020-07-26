package com.yrx.squirrel.nut.rpc.client;

import com.yrx.squirrel.nut.rpc.contract.IHelloWorld;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by r.x on 2020/7/26.
 */
@Slf4j
public class MainClient {

    public static void main(String[] args) {
        IHelloWorld helloWorld = new HelloWorldProxy();
        String word = helloWorld.hello("hello world");
        log.info("word = " + word);
    }
}
