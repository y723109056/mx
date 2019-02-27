package com.mx.sql.driver;


import com.mx.sql.config.IDbConfig;
import com.mx.sql.config.IDbConfigFactory;

/**
 * 数据库操作组件供应构造器
 */
public class DataProviderBuilder implements IDataProviderBuilder {

	/** 数据库配置工厂 */
	private IDbConfigFactory configFactory;
    
	/** 得到数据库配置工厂 */
    public IDbConfigFactory getDbConfigFactory()
    {
        return this.configFactory;
    }
    
    /** 设置数据库配置工厂 */
    public void setDbConfigFactory(IDbConfigFactory dbConfigFactory)
    {
        this.configFactory = dbConfigFactory;
    }
    
    /** 构造数据库组件供应对像 */
    @Override
	public IDataProvider build()
    {
        IDbConfig config = this.configFactory.getConfig();
        return this.build(config);
    }

    /** 构造得到数据库组件供应对像 */
    @Override
	public IDataProvider build(String configName)
    {
        IDbConfig config = this.configFactory.getConfig(configName);
        return this.build(config);
    }

    /**
     * 构造得到数据库组件供应对像
     */
    @Override
	public IDataProvider build(IDbConfig config)
    {
        return new LocalThreadSqlDataProvider(config);
    }
	
}
