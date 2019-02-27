package com.mx.sql.command;


import com.mx.sql.SqlDriverException;
import com.mx.sql.config.IDbConfig;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by zhusw on 2017/12/19.
 */
public class LocalThreadSqlConnection  implements IDbConnection {
    protected static ThreadLocal<Boolean> transactionThreadLocal = new ThreadLocal<Boolean>();
    protected static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    protected LocalThreadSqlConnection() {

    }

    public LocalThreadSqlConnection(IDbConfig dbConfig)
    {
        this.dbConfig=dbConfig;
    }

    /** 数据库配置接口 */
    protected IDbConfig dbConfig;

    /**
     * 关闭数据库连接
     */
    @Override
    public void close() {
        if(this.getConnection()!=null)
        {
            try {
                if(!this.getTransaction())          //事务中不关闭数据库连接
                    this.getConnection().close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 打开数据库连接
     */
    @Override
    public void open()
    {
        if(this.getConnection()==null)
        {
            try {
                connectionThreadLocal.set(this.dbConfig.getConnection());
            }
            catch(SQLException e)
            {
                throw new SqlDriverException("得到数据库连接出错",e);
            }
            catch(Exception e)
            {
                throw new SqlDriverException("连接数据库出错",e);
            }
        }
        else
        {
            try {
                boolean isClose=this.getConnection().isClosed();
                if(isClose){
                    connectionThreadLocal.set(this.dbConfig.getConnection());
                }
            } catch (SQLException e) {
                throw new SqlDriverException("得到数据库连接出错",e);
            }
            catch(Exception e)
            {
                throw new SqlDriverException("连接数据库出错",e);
            }
        }
    }

    /**
     * 判断数据库连接是否关闭
     */
    @Override
    public boolean isClose() {
        if(this.getConnection()==null)return true;
        else
        {
            try {
                if(this.getConnection().isClosed())return true;

            } catch (SQLException e) {
                throw new SqlDriverException("查看数据库连接是否关闭出错",e);
            }
        }
        return false;
    }

    /**
     * 提交事务
     */
    public void commit() throws SQLException {
        if(!this.isClose()){
            this.getConnection().commit();
        }
    }

    /**
     * 回滚事务
     */
    public void rollback(){
        if(!this.isClose()){
            try {
                this.getConnection().rollback();
            } catch (SQLException e) {
                throw new SqlDriverException("数据库连接回滚事务出错",e);
            }
        }
    }

    /**
     * 设置是否开启事务
     * @param transaction
     */
    public void setTransaction(boolean transaction){
        transactionThreadLocal.set(transaction);
    }

    /**
     * 获得是否开启事务状态
     * @return
     */
    public boolean getTransaction(){
        Boolean transaction = transactionThreadLocal.get();
        if(transaction!=null){
            return transaction;
        }
        return false;
    }

    /**
     * 创建一个数据库操作类
     */
    @Override
    public IDbCommand createCommand()
    {
        return new SqlCommand(this);
    }

    /**
     * 释放数据库连接
     */
    @Override
    public void dispose() {
        if(this.getConnection()!=null)
        {
            try {
                if(!this.getTransaction()) {
                    this.getConnection().close();
                    connectionThreadLocal.remove();
                }
            } catch (SQLException e) {
                throw new SqlDriverException("半闭数据库连接出错",e);
            }
        }
    }
    /**
     * 得到jdbc连接对像
     */
    @Override
    public Connection getConnection() {
        return connectionThreadLocal.get();
    }


}
