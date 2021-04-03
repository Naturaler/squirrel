package com.yrx.dawdler.component;

import com.yrx.dawdler.bo.DbMetadataBO;

/**
 * Created by r.x on 2021/4/4.
 */
public interface IDbMetadataHelper {

    DbMetadataBO getTableMetadata(String table);
}
