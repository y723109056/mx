package com.mx.sql.util;

import com.mx.collection.DataRow;
import com.mx.collection.DataTable;
import com.mx.reader.Reader;
import com.mx.spring.SpringContext;
import com.mx.sql.SqlDriverException;
import com.mx.sql.SqlExecuteException;
import com.mx.sql.builder.SqlString;
import com.mx.sql.command.IDataReader;
import com.mx.sql.command.IDbCommand;
import com.mx.sql.command.IDbConnection;
import com.mx.sql.config.IDbConfig;
import com.mx.sql.dialect.IDialect;
import com.mx.sql.driver.IDataProvider;
import com.mx.sql.driver.IDataProviderBuilder;
import com.mx.sql.meta.AbstractDbMetaAccess;
import com.mx.sql.meta.Column;
import com.mx.sql.meta.IDbMetaAccess;
import com.mx.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

/**
 * Sql封装实现类
 */
public class SqlHelper implements ISqlHelper {
    protected static final String DriverProviderBuilderKey = "DriverProviderBuilder";
    protected IDbMetaAccess dbMetaAccess;
    private static Log log = LogFactory.getLog(SqlHelper.class);

    /**
     * 当前访问的数据库接口
     */
    protected IDataProvider provider;

    protected SqlHelper() {
    }

    /**
     * 得到当前访问的数据库接口
     */
    public IDataProvider getDriver()
    {
        return this.provider;
    }

    /**
     * 设置当前访问的数据库接口
     * @param provider
     */
    public void setDriver(IDataProvider provider)
    {
        this.provider=provider;
    }

    /**
     * 得到数据库方言
     */
    public IDialect getDialect()
    {
        return this.provider.getDialect();
    }

    /**
     * 得到数量库配置
     * @return
     */
    public IDbConfig getConfig(){
        return this.provider.getConfig();
    }

    public IDbMetaAccess getDbMeta() {
        if(this.dbMetaAccess==null)dbMetaAccess = new DbMetaAccessProxy(this);
        return this.dbMetaAccess;
    }

    /**
     * 当前访问的数据库接口配置工厂
     * @return
     */
    public static IDataProviderBuilder getBuilder(){
        return (IDataProviderBuilder) SpringContext.instance().get(DriverProviderBuilderKey);
    }

    public SqlHelper(IDataProvider provider){
        this.provider = provider;
    }

    /**
     * 得到默认数据库操作类
     * @return
     */
    public static ISqlHelper instance(){
        IDataProvider provider = getBuilder().build();
        return new SqlHelper(provider);
    }

    /**
     * 得到其他数据库操作类
     * @param configName
     * @return
     */
    public static ISqlHelper instance(String configName){
        IDataProvider provider = getBuilder().build(configName);
        return new SqlHelper(provider);
    }

    /**
     * 得到其他数据库操作类
     * @param config
     * @return
     */
    public static ISqlHelper instance(IDbConfig config)
    {
        IDataProvider provider = getBuilder().build(config);
        return new SqlHelper(provider);
    }

    /**
     * 测试数据库连接
     */
    public static void testConnection(){
        IDataProvider provider = getBuilder().build();
        IDbConnection conn = provider.getConnection();
        conn.open();
        conn.close();
    }

    /**
     * 执行Sql语句
     * @param sqlString
     */
    public static void execute(String sqlString)
    {
        ISqlHelper ish=instance();
        ish.executeNoneQuery(sqlString);
        ish.dispose();
    }


    /**
     * 执行Sql语句
     * @param sqlString
     */
    public static void execute(SqlString sqlString){
        ISqlHelper ish=instance();
        ish.executeNoneQuery(sqlString);
        ish.dispose();
    }

    /**
     * 执行指定数据库的Sql语句
     * @param sqlString
     */
    public static void execute(String sqlString,String config){
        ISqlHelper ish=instance(config);
        ish.executeNoneQuery(sqlString);
        ish.dispose();
    }

    /**
     * 执行指定数据库的Sql语句
     * @param sqlString
     */
    public static void execute(SqlString sqlString,String config){
        ISqlHelper ish=instance(config);
        ish.executeNoneQuery(sqlString);
        ish.dispose();
    }

    /**
     * 返回一个数据库数据读取接口
     */
    public IDataReader getDataReader(String sqlString){
        this.setSqlCommand(sqlString);
        return this.provider.getCommand().executeReader();
    }

    /**
     * 执行Sql语句返回第一行第一列
     */
    public Object executeScalar(String sqlString){
        this.setSqlCommand(sqlString);
        Object obj = this.provider.getCommand().executeScalar();
        this.provider.getConnection().close();
        return obj;
    }


    /**
     * 执行Sql语句返回第一行第一列
     */
    public Object executeScalar(SqlString sqlString){
        this.setSqlCommand(sqlString);
        IDbCommand command = this.provider.getCommand();
        Object obj = command.executeScalar();
        sqlString.setExecTime(command.getExecTime());
        this.provider.getConnection().close();
        return obj;
    }

    /**
     * 重重执行Sql语句返回
     * @param sqlString
     * @param repeat 重复执行数
     * @return
     */
    public List<Object> executeScalar(String sqlString,int repeat){
        this.setSqlCommand(sqlString);
        List<Object> objs = new ArrayList<Object>();
        for(int i=0;i<repeat;i++){
            Object obj = this.provider.getCommand().executeScalar();
            objs.add(obj);
        }
        this.provider.getConnection().close();
        return objs;
    }

    /**
     * 执行Sql语句返回第一个列
     */
    public List<Object> executeScalars(String sqlString){
        this.setSqlCommand(sqlString);
        List<Object> objs = this.provider.getCommand().executeScalars();
        this.provider.getConnection().close();
        return objs;
    }


    /**
     * 执行Sql语句返回第一列
     */
    public List<Object> executeScalars(SqlString sqlString){
        this.setSqlCommand(sqlString);
        IDbCommand command = this.provider.getCommand();
        List<Object> objs = command.executeScalars();
        sqlString.setExecTime(command.getExecTime());
        this.provider.getConnection().close();
        return objs;
    }

    /**
     * 执行Sql语句,返回引响行数
     */
    public int executeNoneQuery(String sqlString){
        this.setSqlCommand(sqlString);
        int[] obj = this.provider.getCommand().executeNonQuery();
        this.provider.getConnection().close();
        return (obj!=null && obj.length>0)?obj[0]:0;
    }

    /**
     * 批量执行Sql语句,返回引响行数
     */
    public int[] executeNoneQuery(String[] sqlStrings){
        this.setSqlCommand(sqlStrings);
        int[] obj = this.provider.getCommand().executeNonQuery();
        this.provider.getConnection().close();
        return obj;
    }

    /**
     * 执行Sql语句,返回引响行数
     */
    public int executeNoneQuery(SqlString sqlString){
        this.setSqlCommand(sqlString);
        IDbCommand command = this.provider.getCommand();
        int[] obj = command.executeNonQuery();
        sqlString.setExecTime(command.getExecTime());
        return (obj!=null && obj.length>0)?obj[0]:0;
    }

    /**
     * 批量执行Sql语句,返回引响行数
     */
    public int[] executeNoneQuery(SqlString[] sqlStrings){
        this.setSqlCommand(sqlStrings);
        IDbCommand command = this.provider.getCommand();
        int[] obj = command.executeNonQuery();
        for (SqlString sql : sqlStrings) {
            sql.setExecTime(command.getExecTime());
        }
        this.provider.getConnection().close();
        return obj;
    }

    /**
     * 执行Sql语句,返回第一行的Map数据对像
     * @param sqlString
     * @return
     */
    public Map<String,Object> getMap(String sqlString){
        DataTable table = this.getDataTable(sqlString);
        if(table.rowSize()>0){
            return table.getRow(0).toMap();
        }
        return null;
    }

    /**
     * 执行Sql语句,返回第一行的Map数据对像
     * @param sqlString
     * @return
     */
    public Map<String,Object> getMap(SqlString sqlString){
        DataTable table = this.getDataTable(sqlString);
        if(table.rowSize()>0){
            return table.getRow(0).toMap();
        }
        return null;
    }

    /**
     * 执行Sql语句,返回MapList数据对像
     * @param sqlString
     * @return
     */
    public List<Map<String,Object>> getMapList(String sqlString){
        DataTable table = this.getDataTable(sqlString);
        return table.toMapList();
    }

    /**
     * 执行Sql语句,返回MapList数据对像
     * @param sqlString
     * @return
     */
    public List<Map<String,Object>> getMapList(SqlString sqlString){
        DataTable table = this.getDataTable(sqlString);
        return table.toMapList();
    }

    /**
     * 执行Sql语句,返回一个数据结果集
     */
    public DataTable getDataTable(String sqlString, String tableName) {
        this.setSqlCommand(sqlString);
        try{
            IDataReader reader = this.provider.getCommand().executeReader();
            DataTable table = this.getDataTable(tableName, reader);
            return table;
        }
        catch(SqlExecuteException e){
            throw e;
        }
        catch(SqlDriverException e){
            throw e;
        }
        catch(Exception e){
            throw new SqlExecuteException(StringUtil.format("执行Sql {0} 返回 DataTable 时出错", sqlString),e);
        }
        finally{
            this.provider.getConnection().close();
        }
    }

    /**
     * 执行Sql语句,返回一个数据结果集
     */
    public DataTable getDataTable(String sqlString) {
        return this.getDataTable(sqlString,"");
    }

    protected DataTable getDataTable(String tableName,IDataReader reader){
        DataTable table=new DataTable(tableName);
        String[] columnNames = reader.getColumnNames();
        boolean logFlag = log.isDebugEnabled();
        String columnInfo = "[";
        String rowInfo = "";
        for(String colName : columnNames){
            if(!table.contains(colName))table.addColumn(colName);
            else throw new SqlExecuteException("返回数据集结果列 "+colName+" 重名");
            if(logFlag)columnInfo+=(columnInfo.length()>1)?","+colName:colName;
        }
        if(logFlag)columnInfo+="]";
        if(logFlag)log.info(columnInfo);

        Object colValue = null;
        while(reader.read()){
            DataRow row = table.newRow();
            if(logFlag)rowInfo="[";
            for(int i=0;i<columnNames.length;i++){
                colValue = reader.get(i+1);
                row.set(i,colValue);
                if(colValue!=null){
                    if(logFlag)rowInfo+=(rowInfo.length()>1)?","+colValue:colValue;
                }else{
                    if(logFlag)rowInfo+=(rowInfo.length()>1)?","+"null":"null";
                }
            }
            if(logFlag)rowInfo+="]";
            if(logFlag)log.info(rowInfo);
        }
        reader.close();

        return table;
    }

    /**
     * 执行Sql语句,返回一个数据结果集
     */
    public DataTable getDataTable(SqlString sqlString, String tableName) {
        this.setSqlCommand(sqlString);
        try{
            IDbCommand command = this.provider.getCommand();
            IDataReader reader = command.executeReader();
            sqlString.setExecTime(command.getExecTime());
            DataTable table = this.getDataTable(tableName, reader);
            return table;
        }
        catch(SqlExecuteException e){
            throw e;
        }
        catch(SqlDriverException e){
            throw e;
        }
        catch(Exception e){
            throw new SqlExecuteException(StringUtil.format("执行Sql {0} 返回 DataTable 时出错",sqlString.toString()),e);
        }
        finally{
            this.provider.getConnection().close();
        }
    }

    /**
     * 执行Sql语句,返回一个数据结果集
     */
    public DataTable getDataTable(SqlString sqlString) {
        return this.getDataTable(sqlString,"");
    }

    /**
     * 返回一个数据库数据读取接口
     */
    public IDataReader getDataReader(SqlString sqlString){
        this.setSqlCommand(sqlString);
        IDbCommand command = this.provider.getCommand();
        IDataReader reader = command.executeReader();
        sqlString.setExecTime(command.getExecTime());
        return reader;
    }

    /**
     * 设置Sql语句
     * @param sqlString
     */
    private void setSqlCommand(String sqlString){
        String sql = SqlRepair.repair(sqlString,this.provider.getDialect());
        sql = getWrapperSql(sql);
        this.provider.getCommand().setSql(sql);
        if(log.isInfoEnabled())log.info(sql);
    }

    private void setSqlCommand(String[] sqlStrings){
        String[] sqls = new String[sqlStrings.length];
        for(int i=0;i<sqlStrings.length;i++){
            String sql = SqlRepair.repair(sqlStrings[i],this.provider.getDialect());
            sqls[i] = getWrapperSql(sql);
            this.provider.getCommand().setSql(sqls);
        }
        if(log.isInfoEnabled())log.info(sqls);
    }

    /**
     * 设置Sql语句
     * @param sqlString
     */
    private void setSqlCommand(SqlString sqlString){
        String sql = SqlRepair.repair(sqlString.toString(),this.provider.getDialect());
        sql = getWrapperSql(sql);
        this.provider.getCommand().setSql(sql,sqlString.getParamValues());
        if(log.isInfoEnabled()){
            log.info(sql);
            if(sqlString.getParamValues()!=null){
                String paramInfo = "[";
                for(Object param : sqlString.getParamValues()){
                    if(param!=null){
                        paramInfo+=(paramInfo.length()>1)?","+param:param;
                    }
                }
                paramInfo+="]";
                log.info(paramInfo);
            }
        }
        sqlString.clear();
    }

    /**
     * 设置Sql语句
     * @param sqlStrings
     */
    private void setSqlCommand(SqlString[] sqlStrings){
        if(sqlStrings.length>0){
            String sql = SqlRepair.repair(sqlStrings[0].toString(),this.provider.getDialect());
            sql = getWrapperSql(sql);

            List<Object[]> paramsList = new ArrayList<Object[]>();
            for(SqlString sqlString : sqlStrings){
                paramsList.add(sqlString.getParamValues());
            }
            this.provider.getCommand().setSql(sql,paramsList);

            if(log.isInfoEnabled()){
                log.info(sql);

                for(SqlString sqlString : sqlStrings){
                    if(sqlString.getParamValues()!=null){
                        String paramInfo = "[";
                        for(Object param : sqlString.getParamValues()){
                            if(param!=null){
                                paramInfo+=(paramInfo.length()>1)?","+param:param;
                            }
                        }
                        paramInfo+="]";
                        log.info(paramInfo);
                    }
                }
            }
            
            for(SqlString sqlString : sqlStrings){
            	sqlString.clear();
            }
        }
    }

    /**
     * 释放数据库资源
     */
    public void dispose() {
        this.provider.dispose();
    }

    static class SqlRepair
    {
        private static Map<String,String> sqlCache;

        static
        {
            sqlCache = new ConcurrentHashMap<String,String>();
        }

        public static String repair2(String sqlString,IDialect dialect)
        {
            if(!sqlCache.containsKey(sqlString))
            {
                String sql = repairQuote(sqlString,dialect);
                sqlCache.put(sqlString, sql);
            }
            return sqlCache.get(sqlString);
        }
        
        public static String repair(String sqlString,IDialect dialect){
        	return sqlString;
        }

        private static String repairQuote(String sqlString,IDialect dialect)
        {
            String result=sqlString;

            java.util.regex.Pattern p = java.util.regex.Pattern.compile("\"\\w*\"");
            Matcher m = p.matcher(sqlString);

            while(m.find())
            {
                if(dialect.hasQuote())
                {
                    String unQuote = m.group().substring(1,m.group().length()-1);
                    result=result.replace(m.group(),dialect.quote(unQuote));
                }
                else
                {
                    String unQuote = m.group().substring(1,m.group().length()-1);
                    result=result.replace(m.group(),unQuote);
                }
            }
            return result;
        }
    }
    
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @param top
	 * @return
	 */
	public IDataReader getDataReader(String sqlString,int top){
		String sql = this.getDialect().getTopString(sqlString, top);
		return this.getDataReader(sql);
	}
	
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @param top
	 * @return
	 */
	public IDataReader getDataReader(SqlString sqlString,int top){
		SqlString sql = this.getDialect().getTopString(sqlString, top);
        return this.getDataReader(sql);
	}

    /**
     *
     */
    public IDataReader getDataReader(SqlString sqlString,int offset,int limit)
    {
        if(offset>0 || limit >0)
        {
            if (this.getDialect().supportsLimitOffset())
            {
                SqlString sql = this.getDialect().getLimitString(sqlString, offset, limit);
                return this.getDataReader(sql);
            }
            else if (this.getDialect().supportsLimit())
            {
                if(offset>0)
                {
                    SqlString sql = this.getDialect().getLimitString(sqlString, 0, limit);
                    IDataReader reader = this.getDataReader(sql);
                    for (int i = 0; i < offset; i++)reader.read();
                    return reader;
                }
                else
                {
                    SqlString sql = this.getDialect().getLimitString(sqlString, 0, limit);
                    return this.getDataReader(sql);
                }
            }
            throw new SqlExecuteException("当前数据库不支持分页");
        }
        else return this.getDataReader(sqlString);
    }

    /**
     * 返回一个数据库数据读取接口
     */
    public IDataReader getDataReader(String sqlString, int offset, int limit) {
        if(offset>0 || limit >0)
        {
            if (this.getDialect().supportsLimitOffset())
            {
                String sql = this.getDialect().getLimitString(sqlString, offset, limit);
                return this.getDataReader(sql);
            }
            else if (this.getDialect().supportsLimit())
            {
                if(offset>0)
                {
                    String sql = this.getDialect().getLimitString(sqlString, 0, limit);
                    IDataReader reader = this.getDataReader(sql);
                    for (int i = 0; i < offset; i++)reader.read();
                    return reader;
                }
                else
                {
                    String sql = this.getDialect().getLimitString(sqlString, 0, limit);
                    return this.getDataReader(sql);
                }
            }
            throw new SqlExecuteException("当前数据库不支持分页");
        }
        else return this.getDataReader(sqlString);
    }

    public DataTable getDataTable(String sqlString, int offset, int limit) {
        return this.getDataTable(sqlString, offset, limit, "");
    }

    public DataTable getDataTable(SqlString sqlString, int offset, int limit) {
        return this.getDataTable(sqlString, offset, limit, "");
    }

    public DataTable getDataTable(String sqlString, int offset, int limit,
                                  String tableName) {
        try{
            IDataReader reader = this.getDataReader(sqlString, offset, limit);
            DataTable table = this.getDataTable(tableName, reader);
            this.provider.getConnection().close();
            return table;
        }catch(SqlExecuteException e){
            throw e;
        }
        catch(SqlDriverException e){
            throw e;
        }
        catch(Exception e){
            throw new SqlExecuteException(StringUtil.format("执行Sql {0} 返回 DataTable 时出错",sqlString.toString()),e);
        }
        finally{
            this.provider.getConnection().close();
        }
    }

    public DataTable getDataTable(SqlString sqlString, int offset, int limit,
                                  String tableName) {
        try{
            IDataReader reader = this.getDataReader(sqlString, offset, limit);
            DataTable table = this.getDataTable(tableName, reader);
            this.provider.getConnection().close();
            return table;
        }catch(SqlExecuteException e){
            throw e;
        }
        catch(SqlDriverException e){
            throw e;
        }
        catch(Exception e){
            throw new SqlExecuteException(StringUtil.format("执行Sql {0} 返回 DataTable 时出错",sqlString.toString()),e);
        }
        finally{
            this.provider.getConnection().close();
        }
    }

    /**
     * 通过Sql返回结果集列信息
     * @return
     */
    public List<Column> getResultColumns(String sqlString){
        this.setSqlCommand(sqlString);
        try{
            IDataReader reader = this.provider.getCommand().executeReader();
            return reader.getColumns();
        }
        catch(SqlExecuteException e){
            throw e;
        }
        catch(SqlDriverException e){
            throw e;
        }
        catch(Exception e){
            throw new SqlExecuteException(StringUtil.format("执行Sql {0} 返回 结果集列信息时出错",sqlString),e);
        }
        finally{
            this.provider.getConnection().close();
        }
    }

    /**
     * 通过表名获取列信息
     * @param tableName
     * @return
     */
    public List<Column> getTableColumns(String tableName){
        List<Column> columns = new ArrayList<Column>();
        String sqlString = this.getDialect().getMetaSql().getFieldsSql(tableName);
        DataTable table = this.getDataTable(sqlString);
        for(DataRow row : table){
            Column col = new Column();
            String fieldType = Reader.stringReader().getValue(row, "FIELD_TYPE");
            String fieldName = Reader.stringReader().getValue(row, "FIELD_NAME").trim();
            Integer fieldLength = Reader.intReader().getValue(row, "FIELD_LENGTH");
            Integer precision = Reader.intReader().getValue(row, "FIELD_PRECISION");
            Integer scale = Reader.intReader().getValue(row, "FIELD_SCALE");
            String remark = (String)Reader.stringReader().getValue(row, "FIELD_REMARK");
            String fieldTypeName = StringUtil.isNumber(fieldType) ? this.getDialect().getMetaSql().getType(Integer.parseInt(fieldType)).toString().toUpperCase() : getAdapterFileTypeName(fieldType, fieldLength, precision, scale);
            col.setLabel(fieldName);
            col.setName(fieldName);
            col.setLength(fieldLength);
            if(precision!=null)col.setPrecision(precision);
            if(scale!=null)col.setScale(scale);
            col.setTypeName(fieldTypeName);
            col.setTableName(tableName);
            col.setComment(remark);
            columns.add(col);
        }
        return columns;
    }

    private String getAdapterFileTypeName(String fieldType, Integer fieldLength, Integer precision, Integer scale){
        if ("NUMBER".equals(fieldType)){
            if (fieldLength.intValue() == 22 && (scale == null || scale.intValue() == 0))
                return "INTEGER";
            if (fieldLength.intValue() == 22 && scale.intValue() > 0)
                if (precision.intValue() > 15)
                    return "DECIMAL";
                else
                    return "DOUBLE";
        } else if ("TIMESTAMP(6)".equals(fieldType))
            return "TIMESTAMP";
        return fieldType;
    }

    /**
     * 得到包装过的Sql语句
     * @param sqlString
     * @return
     */
    private String getWrapperSql(String sqlString){
        String fix = this.getConfig().getOption("sqlSelectSuffix");
        if(!StringUtil.isNullOrEmpty(fix)){
            if(isSelectSqlString(sqlString)){
                return sqlString+fix;
            }
        }
        return sqlString;
    }

    /**
     * 判读是否为select语句
     * @return
     */
    public static boolean isSelectSqlString(String sqlString){
        Matcher m = java.util.regex.Pattern.compile("[A-Za-z]{6}").matcher(sqlString);
        if(m.find()){
            String str = m.group();
            if(!StringUtil.isNullOrEmpty(str) && "select".equals(str.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    /**
     * 开始事务处理
     */
    public void beginTransaction(){
        this.provider.getConnection().setTransaction(true);
    }

    /**
     * 结束事务处理
     */
    public void endTransaction(boolean errorFlag){
        try {
            if(!errorFlag) {                //有异常不提交
                this.provider.getConnection().commit();
            }
        }catch(SQLException e){
            this.provider.getConnection().rollback();
        }finally {
            this.provider.getConnection().setTransaction(false);
            this.provider.getConnection().close();
        }
    }

    class DbMetaAccessProxy extends AbstractDbMetaAccess
    {
        private ISqlHelper ish;

        public DbMetaAccessProxy(ISqlHelper ish)
        {
            super(ish.getDialect().getMetaSql());
            this.ish = ish;
        }

        protected void execute(String[] sqlStrings) {
            for(String sqlString : sqlStrings)
                ish.executeNoneQuery(sqlString);
        }

        protected IDataReader getResult(String sqlString) {
            return ish.getDataReader(sqlString);
        }

        protected Object getValue(String sqlString) {
            return ish.executeScalar(sqlString);
        }

        protected List<Object> getValues(String sqlString) {
            return ish.executeScalars(sqlString);
        }

    }

}
