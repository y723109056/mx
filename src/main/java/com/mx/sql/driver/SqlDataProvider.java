package com.mx.sql.driver;


import com.mx.sql.command.SqlConnection;
import com.mx.sql.config.IDbConfig;
import com.mx.sql.dialect.IDialect;
import com.mx.util.StringUtil;

/**
 * 数据库Sql组件供应类
 */
public class SqlDataProvider extends AbstractDataProvider {

	protected SqlDataProvider() {
	}

	public SqlDataProvider(IDbConfig config)
    {
        this.onit(config);
    }

	/**
	 * 初始化数组库组件供应类
	 * @param config
	 */
    private void onit(IDbConfig config)
    {
        this.config = config;
        this.connection = new SqlConnection(config);
        
        if(!StringUtil.isNullOrEmpty(config.getDialectClass())){
        	try {
				Class<?> clazz = Class.forName(config.getDialectClass());
				IDialect dialect = (IDialect)clazz.newInstance();
				this.setDialect(dialect);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        	
        }
		this.command = this.connection.createCommand();
    }
}
