package com.yrx.squirrel.nut.concurrent.java.chain;

import java.util.List;

/**
 * Created by r.x on 2020/10/12.
 */
public class Chain {
    private List<ChainHandler> handlers;

    int index = 0;

    public Chain(List<ChainHandler> handlers) {
        this.handlers = handlers;
    }

    public void execute() {
        if (index >= handlers.size()) {
            return;
        }
        handlers.get(index++).execute(this);
    }
}
