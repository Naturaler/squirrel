package com.yrx.squirrel.nut.rpc.client;

import com.yrx.squirrel.nut.rpc.client.component.SocketHelper;
import com.yrx.squirrel.nut.rpc.contract.IHelloWorld;
import com.yrx.squirrel.nut.rpc.contract.ParameterDTO;

import java.util.Collections;
import java.util.List;

/**
 * Created by r.x on 2020/7/26.
 */
public class HelloWorldProxy implements IHelloWorld {

    @Override
    public String hello(String content) {
        Class targetObject = IHelloWorld.class;
        String targetMethod = "hello";
        ParameterDTO<String> parameter = new ParameterDTO<>();
        parameter.setName("content");
        parameter.setType("String");
        parameter.setValue(content);
        List<ParameterDTO<String>> parameters = Collections.singletonList(parameter);

        return SocketHelper.send(targetObject, targetMethod, parameters);
    }
}
