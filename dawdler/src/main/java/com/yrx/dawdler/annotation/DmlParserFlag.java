package com.yrx.dawdler.annotation;

import net.sf.jsqlparser.statement.Statement;

import java.lang.annotation.*;

/**
 * Created by r.x on 2021/4/3.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DmlParserFlag {
    Class<? extends Statement> target();
}
