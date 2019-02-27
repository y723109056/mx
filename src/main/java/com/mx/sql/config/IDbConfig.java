package com.mx.sql.config;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库配置接口
 */
public interface IDbConfig extends Comparable<IDbConfig> {

    /** 得到配置名称 */
    String getName();
    /** 设置配置名称 */
    void setName(String name);
    /** 得到数据库类型  */
    String getDbType();
    /** 设置数据库类型 */
    void setDbType(String dbType);
    /** 得到数据库连接
     * @throws java.sql.SQLException */
    Connection getConnection() throws SQLException;
    /** 得到数据库方言名称 */
    String getDialectClass();
    /** 设置数据库方言名称 */
    void setDialectClass(String dialectClass);
    /** 是否为默认的数据库连接 */
    boolean isDefault();
    /** 设置默认的数据库连接 */
    void setDefault(boolean isDefault);
    /** 得到其他功能选项 */
    String getOption(String key);

}
