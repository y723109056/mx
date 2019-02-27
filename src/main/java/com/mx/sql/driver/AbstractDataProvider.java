package com.mx.sql.driver;


import com.mx.sql.command.IDbCommand;
import com.mx.sql.command.IDbConnection;
import com.mx.sql.config.IDbConfig;
import com.mx.sql.dialect.IDialect;

/**
 * 数组库组件，供应类
 */
public abstract class AbstractDataProvider implements IDataProvider {
	/** 数据库配置 对像 */
	protected IDbConfig config;
	/** 数据库操作命令对像 */
    protected IDbCommand command;
    /** 数据库方言对像 */
    protected IDialect dialect;
    /** 数据库连接对像 */
    protected IDbConnection connection;

    /** 得到数据库连接对像 */
    @Override
	public IDbConnection getConnection()
    {
        return this.connection;
    }

    /** 得到数据库操作对像 */
    @Override
	public IDbCommand getCommand()
    {
        return this.command;
    }

    /** 得到数据库方言对像 */
    @Override
	public IDialect getDialect()
    {
        return this.dialect;
    }
    
    /** 设置数据库方言对像 */
    public void setDialect(IDialect dialect)
    {
    	this.dialect = dialect;
    }

    /** 得到数据库类型 */
    @Override
	public String getDBType()
    {
        return this.config.getDbType();
    }
    
    /**
     * 得到数据库配置
     */
    @Override
	public IDbConfig getConfig()
    {
    	return this.config;
    }
    
    /** 释放数据库组件 */
    @Override
	public void dispose() {
    	this.command.dispose();
        this.connection.dispose();
	}

}
