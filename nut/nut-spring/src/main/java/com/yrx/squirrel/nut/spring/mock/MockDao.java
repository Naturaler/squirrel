package com.yrx.squirrel.nut.spring.mock;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by r.x on 2020/8/11.
 */
@Repository
public class MockDao {

    List<String> selectAll() {
        return Collections.singletonList("hello world");
    }
}
