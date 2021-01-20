package com.yrx.squirrel.nut.concurrent.java.chain;

import java.util.Arrays;
import java.util.List;

/**
 * Created by r.x on 2020/10/12.
 */
public class Client {

    static class ChainHandlerA extends ChainHandler {
        @Override
        protected void process() {
            System.out.println("chain handler a");
        }
    }
    static class ChainHandlerB extends ChainHandler {
        @Override
        protected void process() {
            System.out.println("chain handler B");
        }
    }
    static class ChainHandlerC extends ChainHandler {
        @Override
        protected void process() {
            System.out.println("chain handler C");
        }
    }

    public static void main(String[] args) {
        List<ChainHandler> handlers = Arrays.asList(
                new ChainHandlerA(),
                new ChainHandlerB(),
                new ChainHandlerC()
        );
        Chain chain = new Chain(handlers);
        chain.execute();
    }

}
