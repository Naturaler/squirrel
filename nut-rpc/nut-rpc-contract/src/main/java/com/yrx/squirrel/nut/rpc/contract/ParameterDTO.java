package com.yrx.squirrel.nut.rpc.contract;

import lombok.Data;

/**
 * Created by r.x on 2020/7/26.
 */
@Data
public class ParameterDTO<T> {
    private String name;
    private String type;
    private T value;

    @Override
    public String toString() {
        return "name='" + name +
                "$type='" + type +
                "$value=" + value;
    }
}
