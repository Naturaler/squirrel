package com.yrx.dawdler.bo;

import lombok.Data;

import java.util.List;

/**
 * Created by r.x on 2021/4/3.
 */
@Data
public class DbMetadataBO {
    private String table;
    private List<DbColumn> columns;

    @Data
    public static class DbColumn {
        private String name;
        /**
         * e.g:
         * VARCHAR2(32)
         * NUMBER(19,5)
         * DATE
         */
        private String type;
    }
}
