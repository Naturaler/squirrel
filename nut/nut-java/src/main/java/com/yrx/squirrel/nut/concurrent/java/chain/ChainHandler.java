package com.yrx.squirrel.nut.concurrent.java.chain;

/**
 * Created by r.x on 2020/10/12.
 */
public abstract class ChainHandler {

    public void execute(Chain chain) {
        process();
        chain.execute();
    }

    protected abstract void process();
}
