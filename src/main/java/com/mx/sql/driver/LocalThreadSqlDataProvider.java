package com.mx.sql.driver;


import com.mx.sql.command.LocalThreadSqlConnection;
import com.mx.sql.config.IDbConfig;
import com.mx.sql.dialect.IDialect;
import com.mx.util.StringUtil;

public class LocalThreadSqlDataProvider extends AbstractDataProvider {

    protected LocalThreadSqlDataProvider() {
    }

    public LocalThreadSqlDataProvider(IDbConfig config)
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
        this.connection = new LocalThreadSqlConnection(config);

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
