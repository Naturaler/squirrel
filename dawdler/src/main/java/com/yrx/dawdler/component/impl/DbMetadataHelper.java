package com.yrx.dawdler.component.impl;

import com.yrx.dawdler.bo.DbMetadataBO;
import com.yrx.dawdler.component.IDbMetadataHelper;
import oracle.jdbc.OracleType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by r.x on 2021/4/4.
 */
public class DbMetadataHelper implements IDbMetadataHelper {
    private String url;
    private String username;
    private String password;

    public DbMetadataHelper(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public DbMetadataBO getTableMetadata(String table) {
        List<DbMetadataBO.DbColumn> columns = new ArrayList<>();
        try {
            String sql = "select * from user_tab_columns where Table_Name = ?";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, table.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("COLUMN_NAME");
                String dataType = resultSet.getString("DATA_TYPE");
                int dataLength = resultSet.getInt("DATA_LENGTH");
                int dataPrecision = resultSet.getInt("DATA_PRECISION");
                int dataScale = resultSet.getInt("DATA_SCALE");

                DbMetadataBO.DbColumn column = new DbMetadataBO.DbColumn();
                column.setName(name);
                column.setType(combine(dataType, dataLength, dataPrecision, dataScale));
                columns.add(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DbMetadataBO bo = new DbMetadataBO();
        bo.setTable(table);
        bo.setColumns(columns);

        return bo;
    }

    private String combine(String dataType, int dataLength, int dataPrecision, int dataScale) {
        OracleType actual = getOracleType(dataType);
        if (actual == null) {
            return "";
        }
        switch (actual) {
            case VARCHAR2:
                return dataType + "(" + dataLength + ")";
            case NUMBER:
                return dataType + "(" + dataPrecision+","+dataScale + ")";
            case DATE:
                return dataType;
            default:
                return "";

        }
    }

    private OracleType getOracleType(String dataType) {
        OracleType actual = null;
        for (OracleType oracleType : OracleType.values()) {
            if (oracleType.getName().equals(dataType)) {
                actual = oracleType;
            }
        }
        return actual;
    }
}
