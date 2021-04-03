package com.yrx.dawdler.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by r.x on 2021/4/3.
 */
@Getter
@AllArgsConstructor
public enum DdlTypeEnum {
    CREATE_TABLE("CREATE_TABLE", "新增数据表"),
    DROP_TABLE("DROP_TABLE", "删除数据表"),
    MODIFY_TABLE_NAME("MODIFY_TABLE_NAME", "修改表名"),
    ADD_COLUMN("ADD_COLUMN", "新增字段"),
    DROP_COLUMN("DROP_COLUMN", "删除字段"),
    RENAME_COLUMN_NAME("RENAME_COLUMN_NAME", "修改字段名称"),
    MODIFY_COLUMN_LOGIC("MODIFY_COLUMN_LOGIC", "修改字段逻辑"),
    MODIFY_COLUMN_TYPE("MODIFY_COLUMN_TYPE", "修改字段格式"),
    DELETE_PRIMARY_KEY("DELETE_PRIMARY_KEY", "删除主键"),
    MODIFY_PRIMARY_KEY("MODIFY_PRIMARY_KEY", "修改主键"),
    CREATE_INDEX("CREATE_INDEX", "新增主键"),
    ;

    private String code;
    private String desc;

    public static String[] list() {
        String[] result = new String[(DdlTypeEnum.values().length)];
        for (int i = 0; i < DdlTypeEnum.values().length; i++) {
            result[i] = DdlTypeEnum.values()[i].desc;
        }
        return result;
    }
}
