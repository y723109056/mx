package com.mx.entity;

import java.util.Date;

public class SqlLogWithBLOBs extends SqlLog {
    private String sqlString;

    private String sqlParams;

    public SqlLogWithBLOBs(String mapperId, String actionTime, Integer createUser, String sqlString, String sqlParams) {
        super( mapperId, actionTime, createUser);
        this.sqlString = sqlString;
        this.sqlParams = sqlParams;
    }

    public SqlLogWithBLOBs(Integer id, String mapperId, String actionTime, Date createTime, Integer createUser, String sqlString, String sqlParams) {
        super(id, mapperId, actionTime, createTime, createUser);
        this.sqlString = sqlString;
        this.sqlParams = sqlParams;
    }

    public String getSqlString() {
        return sqlString;
    }

    public String getSqlParams() {
        return sqlParams;
    }
}