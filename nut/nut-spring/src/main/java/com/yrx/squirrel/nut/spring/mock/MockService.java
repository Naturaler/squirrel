package com.yrx.squirrel.nut.spring.mock;

import com.yrx.squirrel.nut.spring.util.DateTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by r.x on 2020/8/11.
 */
@Service
@Slf4j
class MockService {
    @Autowired
    private MockDao dao;
    @Autowired
    private TaskExecutor taskExecutor;

    String selectOne() {
        List<String> all = dao.selectAll();
        return all.get(0);
    }

    String executor() throws InterruptedException {
        int size = 10;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            taskExecutor.execute(() -> {
                log.info("thread: {}, date: {}", Thread.currentThread().getName(), new Date());
                countDownLatch.countDown();
            });
        }
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        if (!await) {
            log.error("count down timeout!");
        }
        return "hello world";
    }

    String formatToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(DateTool.today());
    }
}
