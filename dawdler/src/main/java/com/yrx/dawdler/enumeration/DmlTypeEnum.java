package com.yrx.dawdler.enumeration;

import lombok.Getter;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum DmlTypeEnum {
    ADD_DATA("ADD_DATA", "数据新增"),
    MODIFY_DATA("MODIFY_DATA", "数据修改"),
    DELETE_DATA("DELETE_DATA", "数据删除"),
    ;
    private String code;
    private String desc;
    private static final Map<Class<?>, DmlTypeEnum> mapping = new HashMap<>(3);

    DmlTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DmlTypeEnum getByClass(Class<?> source) {
        if (MapUtils.isEmpty(mapping)) {
            synchronized (mapping) {
                if (MapUtils.isEmpty(mapping)) {
                    init();
                }
            }
        }
        return mapping.get(source);
    }

    private static void init() {
        mapping.put(Insert.class, ADD_DATA);
        mapping.put(Delete.class, DELETE_DATA);
        mapping.put(Update.class, MODIFY_DATA);
    }
}
