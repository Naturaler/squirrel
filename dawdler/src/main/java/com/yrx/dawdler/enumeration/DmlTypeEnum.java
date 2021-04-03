package com.yrx.dawdler.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by r.x on 2021/4/3.
 */
@Getter
@AllArgsConstructor
public enum DmlTypeEnum {
    ADD_DATA("ADD_DATA","数据新增"),
    MODIFY_DATA("MODIFY_DATA","数据修改"),
    DELETE_DATA("DELETE_DATA","数据删除"),
    ;

    private String code;
    private String desc;

    public static String[] list() {
        String[] result = new String[(DmlTypeEnum.values().length)];
        for (int i = 0; i < DmlTypeEnum.values().length; i++) {
            result[i] = DmlTypeEnum.values()[i].desc;
        }
        return result;
    }
}
