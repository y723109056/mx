package com.mx.sql.command;

import com.mx.sql.SqlExecuteException;
import com.mx.sql.meta.Column;
import com.mx.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 数据集读取类
 */
public class SqlDataReader implements IDataReader {
    /**
     * jdbc返回结果集
     */
    private ResultSet rs;
    private Statement stmt;

    protected SqlDataReader() {
    }

    public SqlDataReader(Statement stmt,ResultSet rs)
    {
        this.rs=rs;
        this.stmt=stmt;
    }

    /**
     * 读取返回数据集的列名
     */
    @Override
    public String[] getColumnNames()
    {
        try {
            int colCount = rs.getMetaData().getColumnCount();
            String[] cols = new String[colCount];
            for(int i=0;i<colCount;i++)
            {
                //cols[i] = rs.getMetaData().getColumnName(i+1);
                cols[i] = rs.getMetaData().getColumnLabel(i+1);
            }
            return cols;
        } catch (SQLException e) {
            throw new SqlExecuteException("读取返回数据集的列名时出错 \r\tError:"+e.toString());
        }
    }

    /**
     * 读取返回数据集的列信息
     * @return
     */
    public List<Column> getColumns(){
        List<Column> list = new ArrayList<Column>();
        try {
            ResultSetMetaData md = rs.getMetaData();
            int colCount = md.getColumnCount();
            for(int i=0;i<colCount;i++){
                Column col = new Column();
                int index = i+1;
                col.setLabel(md.getColumnLabel(index));
                col.setName(md.getColumnName(index));
                col.setType(md.getColumnType(index));
                col.setTypeName(md.getColumnTypeName(index));
                try{col.setPrecision(md.getPrecision(index));}catch(Exception e){};
                col.setScale(md.getScale(index));
                col.setClassName(md.getColumnClassName(index));
                col.setSchemaName(md.getSchemaName(index));
                col.setTableName(md.getTableName(index));
                col.setLength(md.getColumnDisplaySize(index));
                list.add(col);
            }
        } catch (SQLException e) {
            throw new SqlExecuteException("读取返回数据集的列信息时出错 \r\tError:"+e.toString());
        }
        return list;
    }

    /**
     * 关闭读取
     */
    @Override
    public void close()
    {
        try
        {
            rs.close();
            stmt.close();
        }
        catch(SQLException e)
        {
            throw new SqlExecuteException("关闭数据库读取时出错",e);
        }
    }

    /**
     * 得到列的值
     */
    @Override
    public Object get(String columnName)
    {
        try {
            return rs.getObject(columnName);
        }
        catch(SQLException e)
        {
            throw new SqlExecuteException(StringUtil.format("得到结果集列的值出错 {0}", columnName),e);
        }
    }

    /** 得到列的值
     * @throws java.io.IOException */
    @Override
    public Object get(int columnIndex)
    {
        int type = -1;
        try {
            if(rs.getObject(columnIndex)==null)return null;
            type = rs.getMetaData().getColumnType(columnIndex);
            switch(type){
                case Types.LONGVARCHAR:
                case Types.VARCHAR:
                case Types.CHAR:
                case Types.VARBINARY:
                case Types.NVARCHAR:
                case Types.NCHAR:
                case Types.LONGNVARCHAR:
                    String str = rs.getString(columnIndex);
                    return (str==null)?"":str;
                case Types.CLOB:
                    Clob clob = rs.getClob(columnIndex);
                    BufferedReader rd = new BufferedReader(clob.getCharacterStream());
                    String s = null;
                    StringBuilder sb=new StringBuilder();
                    while((s = rd.readLine())!= null)sb.append(s);
                    return sb.toString();
                case Types.SMALLINT:
                case Types.INTEGER:
                    return rs.getInt(columnIndex);
                case Types.BIGINT:
                    return rs.getLong(columnIndex);
                case Types.FLOAT:
                    return rs.getFloat(columnIndex);
                case Types.DOUBLE:
                    return rs.getDouble(columnIndex);
                case Types.DECIMAL:
                    return rs.getBigDecimal(columnIndex);
                case Types.DATE:
                    return rs.getDate(columnIndex);
                case Types.TIME:
                    return rs.getTime(columnIndex);
                case Types.TIMESTAMP:
                    return rs.getTimestamp(columnIndex);
                case Types.BIT:
                    return rs.getByte(columnIndex);
                default:
                    return rs.getObject(columnIndex);
            }
        }
        catch(SQLException e){
            throw new SqlExecuteException(StringUtil.format("得到结果集列的值出错 {0} {1}",columnIndex,type),e);
        } catch (IOException e) {
            throw new SqlExecuteException(StringUtil.format("得到结果集列的值出错 {0} {1}",columnIndex,type),e);
        }
    }

    /** 读取下一行 */
    @Override
    public boolean read()
    {
        try {
            return rs.next();
        }
        catch(SQLException e)
        {
            throw new SqlExecuteException("读取数据结果集时出错",e);
        }
    }
}
