package com.yrx.squirrel.nut.concurrent.java.myEnum;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by r.x on 2020/9/4.
 */
public final class EnumUtils {
    private EnumUtils() {

    }

    public static <T extends Enum<T> & BaseEnum> String getDesc(Class<T> enumClass, String code) {
        if (StringUtils.isBlank(code)) {
            return code;
        }
        for (T item : enumClass.getEnumConstants()) {
            if (item.getCode().equals(code)) {
                return item.getDesc();
            }
        }
        return code;
    }

    public static <T extends BaseEnum> String transformToCode(Map<String, T> mapping, String source) {
        T target = transformToObj(mapping, source);
        if (target == null) {
            return source;
        }
        return target.getCode();
    }

    public static <T extends BaseEnum> T transformToObj(Map<String, T> mapping, String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        for (Map.Entry<String, T> entry : mapping.entrySet()) {
            if (entry.getKey().equals(source)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
