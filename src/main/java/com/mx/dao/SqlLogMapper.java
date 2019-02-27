package com.mx.dao;

import com.mx.entity.SqlLog;
import com.mx.entity.SqlLogWithBLOBs;

public interface SqlLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SqlLogWithBLOBs record);

    int insertSelective(SqlLogWithBLOBs record);

    SqlLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SqlLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SqlLogWithBLOBs record);

    int updateByPrimaryKey(SqlLog record);
}