package com.mx.sql.mapping;


import com.mx.sql.SqlMapException;
import com.mx.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 主从表连接分组，把SqlId和主键都一样的分一组，减少SQL查询次数
 */
public class SqlLinkGroup {
    private String sqlId;               //SQL ID
    private String fkey;                //从表的连接字段
    private List<SqlLink> links;        //组内连接

    /**
     * 连接分组构造函数
     * @param sqlId SQL ID
     * @param fkey 从表的连接字段
     */
    public SqlLinkGroup(String sqlId,String fkey){
        this.sqlId = sqlId;
        this.fkey = fkey;
        this.links = new ArrayList<SqlLink>();
    }

    /**
     * 把连接对像添加到分组
     * @param sqlLink 连接对像
     */
    public void addSqlLink(SqlLink sqlLink){
        if(sqlLink!=null) {
            if (sqlId.equals(sqlLink.getSqlId()) && fkey.equals(sqlLink.getFkey())) {
                this.links.add(sqlLink);
            }else{
                throw new SqlMapException(StringUtil.format("添加到分组的SQLID和FKEY必需相同 {0} {1} {2} {3} ", sqlId, fkey, sqlLink.getSqlId(), sqlLink.getFkey()));
            }
        }
    }

    public String getKey(){
        return sqlId+"_"+fkey;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getFkey() {
        return fkey;
    }

    public void setFkey(String fkey) {
        this.fkey = fkey;
    }

    public List<SqlLink> getLinks() {
        return links;
    }

    public void setLinks(List<SqlLink> links) {
        this.links = links;
    }
}
