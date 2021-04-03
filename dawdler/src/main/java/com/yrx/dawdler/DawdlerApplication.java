package com.yrx.dawdler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by r.x on 2021/3/27.
 */
@SpringBootApplication
@Slf4j
public class DawdlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DawdlerApplication.class, args);
        log.info("hello world");
    }
}
