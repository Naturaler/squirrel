package com.yrx.squirrel.nut.spring.handler;

/**
 * Created by r.x on 2020/10/20.
 */
@Handler
public class AHandler implements IHandler {
    @Override
    public String handle() {
        return "i'm handler a";
    }
}
