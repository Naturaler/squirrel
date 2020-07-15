package com.yrx.squirrel.nut.spring.util;

import org.springframework.util.StopWatch;

/**
 * Created by r.x on 2020/7/15.
 * <p>
 * demo: org.springframework.util.StopWatch <br>
 * maven: org.springframework spring-core 5.2.6.RELEASE
 * </p>
 */
public class StopWatchDemo {

    public static void main(String[] args) throws InterruptedException {
        StopWatch clock = new StopWatch();

        clock.start("TaskOneName");
        Thread.sleep(1000 * 3);// 任务一模拟休眠3秒钟
        clock.stop();

        clock.start("TaskTwoName");
        Thread.sleep(1000 * 10);// 任务一模拟休眠10秒钟
        clock.stop();

        clock.start("TaskThreeName");
        Thread.sleep(1000 * 10);// 任务一模拟休眠10秒钟
        clock.stop();

        System.out.println(clock.prettyPrint());
    }

}
