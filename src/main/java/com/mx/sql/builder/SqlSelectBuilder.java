package com.mx.sql.builder;


import com.mx.util.StringUtil;

/**
 * Sql语句组装类
 */
public class SqlSelectBuilder {
	/** 查询部份 */
	private String selectClause;
	/** 表连接部份 */
    private String fromClause;
    /** 条件部份 */
    private String whereClause;
    /** 排序部份 */
    private String orderByClause;
    /** 分组部份 */
    private String groupByClause;
    
    public SqlSelectBuilder()
    {
        this.selectClause = "";
        this.fromClause = "";
        this.orderByClause = "";
        this.groupByClause = "";
        this.whereClause = "";
    }

    /** 设置查询输出Sql */
    public SqlSelectBuilder setSelectClause(String selectClause)
    {
        this.selectClause = selectClause;
        return this;
    }

    /** 设置名表连接Sql */
    public SqlSelectBuilder setFromClause(String fromClause)
    {
        this.fromClause = fromClause;
        return this;
    }

    /** 设置名表连接Sql */
    public SqlSelectBuilder setFromClause(String tableName, String alias)
	{
		this.fromClause = tableName + " " + alias;
		return this;  
	}
    
    /** 设置条件部份Sql */
    public SqlSelectBuilder setWhereClause(String whereSqlString)
    {
        this.whereClause = whereSqlString;
        return this;
    }

    public SqlSelectBuilder setOrderByClause(String orderByClause)
    {
        this.orderByClause = orderByClause;
        return this;
    }

    public SqlSelectBuilder setGroupByClause(String groupByClause)
    {
        this.groupByClause = groupByClause;
        return this;
    }

    public SqlSelectBuilder addSelectCluase(String selectCluase)
    {
        if (StringUtil.isNullOrEmpty(selectCluase)) return null;
        if (!"".equals(this.selectClause))
        {
            this.selectClause = this.selectClause+","+selectCluase;
        }
        else this.selectClause = selectCluase;
        return this;
    }
    
    public SqlSelectBuilder addWhereCondition(String whereCondition)
    {
        if (StringUtil.isNullOrEmpty(whereCondition)) return null;
        if (!"".equals(this.whereClause))
        {
            this.whereClause = StringUtil.format("{0} AND {1}",this.whereClause,whereCondition);
        }
        else this.whereClause = whereCondition;
        return this;
    }

    public SqlSelectBuilder addWhereCondition(String whereCondition,String op)
    {
        if (StringUtil.isNullOrEmpty(whereCondition)) return null;
        if (!"".equals(this.whereClause))
        {
            this.whereClause = StringUtil.format("{0} {1} {2}",this.whereClause,op,whereCondition);
        }
        else this.whereClause = whereCondition;
        return this;
    }

    public SqlSelectBuilder addOrderCluase(String orderbyCluase)
    {
        if (StringUtil.isNullOrEmpty(orderbyCluase)) return null;
        if (!"".equals(this.orderByClause))
        {
            this.orderByClause = this.orderByClause+","+orderbyCluase;
        }
        else this.orderByClause = orderbyCluase;
        return this;
    }
    
    public String toSqlString()
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT ")
            .append(this.selectClause)
            .append(" FROM ")
            .append(this.fromClause);

        if (!StringUtil.isNullOrEmpty(this.whereClause))
        {
        	sb.append(" WHERE ");

            if (!StringUtil.isNullOrEmpty(this.whereClause))
            {
            	sb.append(this.whereClause);
            }
        }

        if (!StringUtil.isNullOrEmpty(this.groupByClause))
        {
        	sb.append(" GROUP BY ")
                .append(this.groupByClause);
        }
        if (!StringUtil.isNullOrEmpty(this.orderByClause))
        {
            sb.append(" ORDER BY ")
                .append(this.orderByClause);
        }

        return sb.toString();
    }
}
