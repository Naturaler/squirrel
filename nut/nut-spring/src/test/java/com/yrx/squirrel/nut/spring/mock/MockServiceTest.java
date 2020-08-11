package com.yrx.squirrel.nut.spring.mock;


import com.yrx.squirrel.nut.spring.util.DateTool;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by r.x on 2020/8/11.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(DateTool.class)
public class MockServiceTest {
    @InjectMocks
    private MockService service;
    @Mock
    private MockDao dao;

    @Test
    public void selectOne() {
        String nut = "hello world";
        List<String> all = Collections.singletonList(nut);
        Mockito.when(dao.selectAll()).thenReturn(all);

        String one = service.selectOne();

        Assert.assertEquals(nut, one);
    }

    /**
     * 通过 FieldSetter 实现 成员变量的注入
     */
    @Test
    public void executor() throws InterruptedException, NoSuchFieldException {
        // 高版本的 FieldSetter 可以直接使用静态方法实现
        FieldSetter fieldSetter = new FieldSetter(service, MockService.class.getDeclaredField("taskExecutor"));
        fieldSetter.set(threadPoolTaskExecutor());

        String actual = service.executor();

        Assert.assertEquals("hello world", actual);
    }

    /**
     * mock 静态函数的方法：<br>
     * 1、在类上添加 <strong>@PrepareForTest(StaticClass.class)</strong> <br>
     * 2、在测试方法内添加 <strong>PowerMockito.mockStatic(StaticClass.class);</strong> <br>
     * 3、mock静态方法 <br>
     * 4、对比结果 <br>
     */
    @Test
    public void formatToday() {
        PowerMockito.mockStatic(DateTool.class);
        Date yesterday = yesterday();
        Mockito.when(DateTool.today()).thenReturn(yesterday);

        String today = service.formatToday();
        System.out.println("today = " + today);
    }

    private TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("junit_test_executor");
        executor.initialize();
        return executor;
    }

    private Date yesterday() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_MONTH, -1);
        return instance.getTime();
    }
}
