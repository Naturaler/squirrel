package com.yrx.squirrel.nut.concurrent.java;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * Created by r.x on 2020/7/11.
 * <p>
 * CountDownLatch的使用demo<br>
 * 通过 CountDownLatch 实现线程阻塞
 * </p>
 */
@Slf4j
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(5);
        latch.countDown();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                log.info(" start sleep");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(" end sleep");
                latch.countDown();
            }, "sub-thread-" + i);
            thread.start();
        }

        log.info("===== main thread await. =====");
        latch.await();
        log.info("===== main thread finishes await. =====");
    }
}
