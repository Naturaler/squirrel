package com.yrx.squirrel.nut.concurrent.java;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by r.x on 2020/7/18.
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 等凉菜
        Callable<String> ca1 = () -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "等凉菜";
        };

        FutureTask<String> ft1 = new FutureTask<>(ca1);
        new Thread(ft1).start();

        //等包子
        Callable<String> ca2 = () -> {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "等包子";
        };


        FutureTask<String> ft2 = new FutureTask<>(ca2);
        new Thread(ft2).start();

        System.out.println(ft1.get());
        System.out.println(ft2.get());

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
