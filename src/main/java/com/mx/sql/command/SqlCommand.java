package com.mx.sql.command;

import com.mx.sql.SqlExecuteException;
import com.mx.util.DateUtil;
import com.mx.util.StringUtil;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



/**
 * 数据库操作命令类
 */
public class SqlCommand implements IDbCommand {
    /** 多条Sql语句 */
    private List<SqlItem> sqls;
    /** 连接对像 */
    private IDbConnection dbConn;
    /** 执行时间  */
    private long execTime;
    private long startTime;

    public SqlCommand(IDbConnection dbConn){
        this.dbConn=dbConn;
        this.sqls = new ArrayList<SqlItem>();
    }

    /**
     * 得到类型，Sql或存贮过程
     */
    public CommandType getCommandType(){
        return CommandType.Text;
    }

    /**
     * 得到一个jdbc操作对像
     * @return
     */
    private Statement getStatement(){
        this.dbConn.open();
        SqlItem item = this.sqls.get(0);
        Integer paramCount = item.getParamCount();
        boolean hasParams = item.getParams()!=null && item.getParams().size()>0;

        if(hasParams){
            PreparedStatement pstmt;
            try {
                pstmt = this.dbConn.getConnection().prepareStatement(item.getSql());
                if(this.sqls.size()>1){
                    pstmt.getConnection().setAutoCommit(false);
                    int line = 1;
                    for(SqlItem sql : this.sqls){
                        if(paramCount==sql.getParamCount()){
                            for(IDbParameter param : sql.getParams()){
                                this.setCommandParameter(pstmt, param);
                            }
                            pstmt.addBatch();
                        }else{
                            throw new SqlExecuteException(StringUtil.format("批量执行 common 出错 原因 传入参数前后不一致   {0} 第 1行  传入参数 {1} 第 {2}行 传入参数  {3} ", this.getSqlString(), paramCount, line, sql.getParamCount()));
                        }
                        line ++;
                    }
                }else{
                    for(IDbParameter param : item.getParams()){
                        this.setCommandParameter(pstmt, param);
                    }
                }
                return pstmt;
            }catch(SQLException e){
                throw new SqlExecuteException(StringUtil.format("得到Statement对像出错 common {0}",this.getSqlString()),e);
            }catch(SqlExecuteException e){
                throw e;
            }
        }else{
            try {
                if(this.sqls.size()>1){
                    Statement st = this.dbConn.getConnection().createStatement();
                    st.getConnection().setAutoCommit(false);
                    for(SqlItem sql : this.sqls){
                        st.addBatch(sql.sql);
                    }
                    return st;
                }else{
                    return this.dbConn.getConnection().createStatement();
                }
            }catch(SQLException e){
                throw new SqlExecuteException(StringUtil.format("得到Statement对像出错 common {0}",this.getSqlString()),e);
            }catch(SqlExecuteException e){
                throw e;
            }
        }
    }

    /**
     * 得到第一条Sql语句
     * @return
     */
    private String getSqlString(){
        if(this.sqls.size()>0){
            return this.sqls.get(0).sql;
        }
        return "";
    }

    private void executeBegin(){
        this.startTime = DateUtil.getCurrentTime();
    }

    private void excuteEnd(){
        this.execTime = DateUtil.getCurrentTime() - this.startTime;
    }

    /**
     * 执行命令，并返回响应行数
     */
    public int[] executeNonQuery(){
        try{
            Statement stmt=this.getStatement();
            boolean flag = false;
            if(this.sqls.size()>1){
                this.executeBegin();
                int[] result = stmt.executeBatch();
                if(!this.dbConn.getTransaction())           //开启事务时不提交
                    stmt.getConnection().commit();
                this.excuteEnd();
                return result;
            }else{
                this.executeBegin();
                if(this.dbConn.getTransaction())            //开启事务时关闭自动提交
                    stmt.getConnection().setAutoCommit(false);
                if(stmt instanceof PreparedStatement){
                    flag = ((PreparedStatement)stmt).execute();
                }else{
                    stmt.execute(this.getSqlString());
                }
                this.excuteEnd();
            }

            int[] result;
            if(flag){
                ResultSet rs=stmt.getResultSet();
                if(rs.next()){
                    int count = rs.getRow();
                    result = new int[]{count};
                }else{
                    result = new int[]{-1};
                }
                rs.close();
            }
            else{
                result = new int[]{stmt.getUpdateCount()};
            }
            stmt.close();
            return result;
        }catch(SQLException e){
            throw new SqlExecuteException(StringUtil.format("执行Sql语句出错 {0}",this.getSqlString()),e);
        }catch(SqlExecuteException e){
            throw e;
        }finally{
            this.dbConn.close();
        }
    }

    private void setCommandParameter(PreparedStatement cstmt,IDbParameter param){
        try {
            switch(param.getValueType()){
                case TinyInt:
                case SmallInt:
                case Integer:
                    cstmt.setInt(param.getIndex(),(Integer)param.getValue());
                    break;
                case Bigint:
                    cstmt.setLong(param.getIndex(),(Long)param.getValue());
                    break;
                case Float:
                    cstmt.setFloat(param.getIndex(),(Float)param.getValue());
                    break;
                case Double:
                    cstmt.setDouble(param.getIndex(),(Double)param.getValue());
                    break;
                case Numeric:
                case Decimal:
                    cstmt.setBigDecimal(param.getIndex(),(BigDecimal)param.getValue());
                    break;
                case Char:
                    cstmt.setString(param.getIndex(),((Character)param.getValue()).toString());
                    break;
                case Varchar:
                case LongVarchar:
                    cstmt.setString(param.getIndex(),(String)param.getValue());
                    break;
                case Clob:
                    cstmt.setClob(param.getIndex(),(Clob)param.getValue());
                    break;
                case Date:
                    cstmt.setDate(param.getIndex(),(Date)param.getValue());
                    break;
                case Time:
                    cstmt.setTime(param.getIndex(),(Time)param.getValue());
                    break;
                case TimeStamp:
                    cstmt.setTimestamp(param.getIndex(),(Timestamp)param.getValue());
                    break;
                case Binary:
                    cstmt.setBinaryStream(param.getIndex(),(InputStream)param.getValue());
                    break;
                case Blob:
                    cstmt.setBlob(param.getIndex(),(Blob)param.getValue());
                    break;
                case Boolean:
                    cstmt.setBoolean(param.getIndex(),(Boolean)param.getValue());
                    break;
                default:
                    Object val = param.getValue();
                    if(val!=null){
                        cstmt.setObject(param.getIndex(),param.getValue());
                    }else{
                        cstmt.setNull(param.getIndex(), Types.NULL);
                    }
                    break;
            }
        }catch (SQLException e) {
            throw new SqlExecuteException(StringUtil.format("设置Sql参数出错 参数类型{0} 参数值 {1} 参数索引 {2} Sql语句 {3} \r\t",param.getValueType(),param.getValue(),param.getIndex(),this.getSqlString()),e);
        }
    }

    /**
     * 执行命令返回，返回一个数据集读取接口
     */
    public IDataReader executeReader(){
        try {
            if(this.sqls.size()>1)throw new SqlExecuteException(StringUtil.format("方法executeReader不支持批量sql {0}",this.getSqlString()));

            Statement stmt=this.getStatement();
            this.executeBegin();
            ResultSet st= (stmt instanceof PreparedStatement)?
                    ((PreparedStatement)stmt).executeQuery():stmt.executeQuery(this.getSqlString());
            this.excuteEnd();
            return new SqlDataReader(stmt,st);

        }catch(SQLException e){
            throw new SqlExecuteException(StringUtil.format("执行Sql语句出错 {0}", this.getSqlString()),e);
        }catch(SqlExecuteException e){
            throw e;
        }
    }

    public List<Object> executeScalars(){
        if(this.sqls.size()>1)throw new SqlExecuteException(StringUtil.format("方法executeScalars不支持批量sql {0}",this.getSqlString()));
        try{
            Statement stmt=this.getStatement();
            this.executeBegin();
            boolean flag = (stmt instanceof PreparedStatement)?
                    ((PreparedStatement)stmt).execute():stmt.execute(this.getSqlString());
            this.excuteEnd();
            List<Object> result=null;
            if(flag){
                ResultSet rs=stmt.getResultSet();
                result = new ArrayList<Object>();
                while(rs.next()){
                    result.add(rs.getObject(1));
                }
                rs.close();
            }
            stmt.close();
            return result;
        }catch(SQLException e){
            throw new SqlExecuteException(StringUtil.format("执行Sql语句出错 {0}", this.getSqlString()),e);
        }
        catch(SqlExecuteException e){
            throw e;
        }finally{
            this.dbConn.close();
        }
    }

    /**
     * 执行命令，返回第一行第一列
     */
    public Object executeScalar(){
        if(this.sqls.size()>1)throw new SqlExecuteException(StringUtil.format("方法executeScalars不支持批量sql {0}",this.getSqlString()));
        try{
            Statement stmt=this.getStatement();
            this.executeBegin();
            boolean flag = (stmt instanceof PreparedStatement)?
                    ((PreparedStatement)stmt).execute():stmt.execute(this.getSqlString());
            this.excuteEnd();
            Object result=null;
            if(flag){
                ResultSet rs=stmt.getResultSet();
                if(rs.next())
                {
                    result = rs.getObject(1);
                }
                rs.close();
            }else{
                result = stmt.getUpdateCount();
            }
            stmt.close();
            return result;
        }catch(SQLException e){
            throw new SqlExecuteException(StringUtil.format("执行Sql语句出错 {0}", this.getSqlString()),e);
        }catch(SqlExecuteException e){
            throw e;
        }finally{
            this.dbConn.close();
        }
    }

    /**
     * 设置Sql语句
     * @param sql
     */
    public void setSql(String sql){
        this.sqls.clear();
        this.sqls.add(new SqlItem(sql));
    }

    /**
     * 设置批量Sql语句
     * @param sqls
     */
    public void setSql(String[] sqls){
        this.sqls.clear();
        for(String sql : sqls){
            this.sqls.add(new SqlItem(sql));
        }
    }

    /**
     * 设置带参数的Sql语句
     * @param sql	如: Select * from myTable where id={0} 或 Select * from myTable where id=?
     * @param params
     */
    public void setSql(String sql,Object[] params){
        this.sqls.clear();
        this.sqls.add(new SqlItem(sql,params));
    }

    /**
     * 设置批准带参数格式的Sql
     * @param sql 有两样格式 格式1: Select * from student where name={0} and sex={1}
     * 						格式2: Select * from student where name=? and sex=?
     * @param paramsList
     */
    public void setSql(String sql,List<Object[]> paramsList){
        this.sqls.clear();
        for(Object[] params : paramsList){
            this.sqls.add(new SqlItem(sql,params));
        }
    }

    /**
     * 设置批准带参数格式的Sql
     * @param sqls 有两样格式 格式1: Select * from student where name={0} and sex={1}
     * 						格式2: Select * from student where name=? and sex=?
     * @param paramsList
     */
    public void setSql(List<String> sqls,List<Object[]> paramsList){
        this.sqls.clear();
        for(int i=0;i<paramsList.size();i++){
            this.sqls.add(new SqlItem(sqls.get(i),paramsList.get(i)));
        }
    }

    /**
     * 释放数据库连接
     */
    public void dispose() {
        if(this.dbConn!=null)
        {
            this.dbConn.close();
        }
    }

    /**
     * 得到执行时间
     * @return
     */
    public long getExecTime(){
        return this.execTime;
    }

    static class SqlItem{
        private String sql;
        private IDbParameterCollection dbParams;

        public String getSql(){
            return this.sql;
        }

        public void setSql(String sql){
            this.sql = sql;
        }

        public Integer getParamCount(){
            if(dbParams!=null){
                return dbParams.size();
            }
            return 0;
        }

        public IDbParameterCollection getParams(){
            return this.dbParams;
        }

        public void setParams(IDbParameterCollection params){
            this.dbParams = params;
        }

        public SqlItem(){
            this.sql = "";
            this.dbParams = null;
        }

        public SqlItem(String sql){
            this.sql = sql;
            this.dbParams = null;
        }

        public SqlItem(String sql,Object[] params){
            this.sql = sql;
            this.dbParams = new SqlParameterCollection(params);
            if(this.sql.indexOf("{0}")>-1)this.prepare();
        }

        private void prepare(){
            if(this.dbParams==null || this.dbParams.size()==0)
                return;

            String formatString=this.sql;

            //这里的作用是只匹配{}里面是数字的子字符串
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\{(\\d+)\\}");
            java.util.regex.Matcher m = p.matcher(this.sql);

            Object[] params=new Object[m.groupCount()];
            int pIndex=0;
            while(m.find()){
                //获取{}里面的数字作为匹配组的下标取值
                int index=Integer.parseInt(m.group(1));

                if(index<this.dbParams.size()){
                    params[pIndex]=this.dbParams.get(index);
                    formatString=formatString.replace(m.group(),"?");
                }
                pIndex++;
            }

            this.sql=formatString;
            this.dbParams.clear();
            this.dbParams.fromArray(params);
        }

    }
}

