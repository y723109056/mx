package com.mx.sql.builder;


import com.mx.util.StringUtil;

/**
 * Sql语句参数对像
 */
public class SqlString {
	/** Sql语句块集合  */
    private Object[] sqlParts;
    /** 是否已整合标志 */
    private boolean isCompact = false;

	/** 执行时间 */
	private long execTime;

    /** Sql语句块数量 */
	public int size()
    {
        return sqlParts.length;
    }
    
	/** 得到参数个数 */
    public int paramSize()
    {
		int count=0;
    	if(sqlParts!=null){
            for(Object part : sqlParts){
            	if(part instanceof Parameter){
            		Parameter param = ((Parameter)part);
            		if(param.toString().equals("?"))
            			count++;
            	}
            }
		}
    	return count;
    }
    
    public Object[] getParts()
    {
    	return sqlParts;
    }
    
    /** 判断Sql语句是否为空 */
    public boolean isEmpty()
    {
    	if(sqlParts!=null){
	    	for(Object obj : sqlParts){
	    		if(obj instanceof String){
	    			if(((String)obj).trim().length()>0)return false;
	    		}else if(obj instanceof Parameter){
	    			if(((Parameter)obj).getValue()!=null)return false;
	    		}else if(obj!=null){
	    			return false;
	    		}
	    	}
    	}
    	return true;
    }
    
    /** 是否包含参数  */
    public boolean hasParameter(){
    	if(sqlParts!=null){
	    	for(Object obj : sqlParts){
	    		if(obj instanceof Parameter){
	    			return true;
	    		}
	    	}
    	}
    	return false;
    }
    
    /** 得到参数值的集合 */
    public Object[] getParamValues(){
    	int paramSize = this.paramSize();
    	Object[] params = new Object[paramSize];
    	if(sqlParts!=null){
    		int index = 0;
			for(Object part : sqlParts){
	        	if(part instanceof Parameter){
	        		Parameter param = ((Parameter)part);
	        		if(param.toString().equals("?")){
		        		if(param.getValue()!=null || param.getType()==null){
		        			params[index] = param.getValue();
		        		}else{
		        			params[index] = param;
		        		}
		        		index++;
	        		}
	        	}
	        }
    	}
		return params;
    }
    
    public SqlString(Object[] sqlParts)
    {
        this.sqlParts = sqlParts;
    }

    public SqlString(String sqlPart)
    {
    	this(!StringUtil.isNullOrEmpty(sqlPart)?new Object[] { sqlPart }:new Object[0]);
    }
    
    public SqlString(Parameter sqlPart)
    {
    	this(new Object[]{sqlPart});
    }
    
    public SqlString(Object sqlPart){
    	this(sqlPart!=null && !"".equals(sqlPart)?new Object[] { sqlPart }:new Object[0]);
    }
    
    /**
     * 拼接两个Sql语句对像
     * @param sqlString
     * @return
     */
    public SqlString contact(SqlString sqlString){
    	if(sqlString!=null){
	    	int len = sqlParts.length+sqlString.size();
	    	Object[] newParts = new Object[len];
	    	System.arraycopy(sqlParts,0,newParts,0,sqlParts.length);
	    	System.arraycopy(sqlString.sqlParts,0,newParts,sqlParts.length,sqlString.size());
	    	this.sqlParts = newParts;
    	}
    	return this;
    }
    
    public SqlString contact(String sql){
    	if(StringUtil.isNotEmpty(sql)){
    		int len = sqlParts.length+1;
    		Object[] newParts = new Object[len];
    		System.arraycopy(sqlParts,0,newParts,0,sqlParts.length);
    		newParts[len-1]=sql;
    		this.sqlParts = newParts;
    	}
    	return this;
    }
    
    public SqlString contact(Parameter param){
    	if(param!=null){
    		int len = sqlParts.length+1;
    		Object[] newParts = new Object[len];
    		System.arraycopy(sqlParts,0,newParts,0,sqlParts.length);
    		newParts[len-1]=param;
    		this.sqlParts = newParts;
    	}
    	return this;
    }
    
    /**
     * 合并整合字符串
     */
    public SqlString compact()
    {
    	if(isCompact){
    		return this;
    	}else{
    		SqlStringBuilder builder = new SqlStringBuilder(sqlParts.length);
    		String currentPart = null;
    		for(int i=0;i<sqlParts.length;i++)
    		{
    			Object part = sqlParts[i];
    			if(part instanceof String)
    			{
    				String strPart = (String)part;
    				if(!StringUtil.isNullOrEmpty(strPart))
    				{
    					if(currentPart==null)currentPart = strPart;
    					else currentPart+=strPart;
    				}
    			}
    			else
    			{
    				if(currentPart!=null)
    				{
    					builder.append(currentPart);
    					currentPart = null;
    				}
    				builder.append(part);
    			}
    		}
    		if(currentPart!=null)builder.append(currentPart);
    		SqlString sqlString = builder.toSqlString();
    		sqlString.isCompact = true;
    		return sqlString;
    	}
    }
    
    public void replacePart(int index,Object sqlPart)
    {
    	if(index<this.sqlParts.length && index > -1)
    	{
    		this.sqlParts[index] = sqlPart;
    	}
    }
    
    public SqlString replaceAll(String regex,String replacement)
    {
    	SqlStringBuilder builder = new SqlStringBuilder(this.size());
    	SqlString sql = this.compact();
    	for(Object part : sql.getParts())
    	{
    		if(part instanceof String)
    		{
    			String strPart = (String)part;
    			if(!StringUtil.isNullOrEmpty(strPart))
    			{
    				builder.append(strPart.replaceAll(regex,replacement));
    			}
    		}
    		else
    		{
    			builder.append(part);
    		}
    	}
    	sql = builder.toSqlString();
    	sql.isCompact = true;
    	return sql;
    }
    
    /**
     * 克隆Sql语句对像
     */
    @Override
	public SqlString clone()
    {
    	Object[] clonedParts = new Object[sqlParts.length];
        System.arraycopy(sqlParts,0,clonedParts,0,sqlParts.length);
        return new SqlString(clonedParts);
    }
    
    /**
     * 比较两个Sql语句对像是否相等
     */
    @Override
	public boolean equals(Object obj)
    {
        // Step1: Perform an equals test
        if (obj == this)
        {
            return true;
        }

        // Step	2: Instance of check
        if(!(obj instanceof SqlString))
        {
        	return false;
        }
        
        SqlString rhs=(SqlString)obj;

        //Step 3: Check each important field

        // if they don't contain the same number of parts then we
        // can exit early because they are different
        if (sqlParts.length != rhs.sqlParts.length)
        {
            return false;
        }

        // they have the same number of parts - so compare each
        // part for equallity.
        for (int i = 0; i < sqlParts.length; i++)
        {
            if (!sqlParts[i].equals(rhs.sqlParts[i]))
            {
                return false;
            }
        }

        // nothing has been found that is different - so they are equal.
        return true;
    }
    
    @Override
	public int hashCode()
    {
        int hashCode = "".hashCode();

        if(sqlParts!=null){
	        for (int i = 0; i < sqlParts.length; i++)
	        {
	            hashCode += sqlParts[i].hashCode();
	        }
        }

        return hashCode;
    }

    /**
     * 输出Sql语句字符串
     */
    @Override
	public String toString()
    {
    	if(sqlParts!=null){
	        StringBuilder builder = new StringBuilder(sqlParts.length * 15);
	
	        for (int i = 0; i < sqlParts.length; i++)
	        {
	            builder.append(sqlParts[i].toString());
	        }
	
	        return builder.toString();
    	}
    	return "";
    }
    
    public void clear(){
    	this.sqlParts = new Object[0];
    	this.isCompact = false;
    }

	public long getExceTime(){
		return this.execTime;
	}

	public void setExecTime(long execTime){
		this.execTime = execTime;
	}

}
