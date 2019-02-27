package com.mx.sql.config;

import java.util.List;

/**
 * 数据库配置接口工厂
 */
public interface IDbConfigFactory {
	
	/**
	 * 得到默认的数据库配置
	 * @return
	 */
	IDbConfig getConfig();

	/**
	 * 通过配置名称得到数据库配置接口
	 * @param name
	 * @return
	 */
    IDbConfig getConfig(String name);
    
    /**
     * 得到所有的数据库配置
     * @return
     */
    List<IDbConfig> getConfigs();
}
