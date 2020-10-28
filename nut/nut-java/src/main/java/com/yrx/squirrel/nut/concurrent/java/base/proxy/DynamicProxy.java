package com.yrx.squirrel.nut.concurrent.java.base.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by r.x on 2020/9/21.
 */
public class DynamicProxy {
    public static void main(String[] args) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method);
                if (method.getName().equals("morning")) {
                    System.out.println("Good morning, " + args[0]);
                } else if (method.getName().equals("night")) {
                    return "good night, " + args[0] + args[1];
                }
                return null;
            }
        };
        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(), // 传入ClassLoader
                new Class[]{Hello.class}, // 传入要实现的接口
                handler); // 传入处理调用方法的InvocationHandler
        hello.morning("Bob");
        String night = hello.night("Anna", "bye");
        System.out.println("night = " + night);
    }
}

interface Hello {
    void morning(String name);

    String night(String name, String addition);
}

