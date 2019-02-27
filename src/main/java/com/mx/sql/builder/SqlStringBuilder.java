package com.mx.sql.builder;

import com.mx.util.StringUtil;

/**
 * Sql拼接类，提高拼接Sql的性能
 */
public class SqlStringBuilder {
	private Object[] parts;
	private int size;
	
	public SqlStringBuilder()
	{
		this(0);
	}
	
	public SqlStringBuilder(int capacity)
	{
		this.size =0;
		this.parts = new Object[capacity];
		this.setCapacity(capacity);
	}
	
	public void append(String sqlString)
	{
		this.append(new Object[]{sqlString});
	}
	
	public void append(String sqlString,Object ... args)
	{
		String sql  = StringUtil.format(sqlString, args);
		this.append(new Object[]{sql});
	}
	
	public void append(SqlString sqlString)
	{
		this.append(sqlString.getParts());
	}
	
	public void append(Object[] sqlparts)
	{
		if(sqlparts!=null && sqlparts.length>0)
		{
			int len = sqlparts.length;
			int max = this.size+len;
			this.ensureCapacity(max);
			for(int i=0;i<len;i++)
			{
				this.parts[this.size] = sqlparts[i];
				this.size++;
			}
		}
	}
	
	public void append(Object sqlpart)
	{
		if(sqlpart!=null)
		{
			this.ensureCapacity(this.size+1);
			this.parts[this.size] = sqlpart;
			this.size++;
		}
	}
	
	/**
     * 确保行容量
     * @param min
     */
    private void ensureCapacity(int min)
    {
        if (this.parts.length < min)
        {
            int num = (this.parts.length == 0) ? 4 : (this.parts.length * 2);
            if (num < min)
            {
                num = min;
            }
            this.setCapacity(num);
        }
    }
    
    /**
     * 设置行容量
     * @param value
     */
    public void setCapacity(int value)
    {
        if (value != this.parts.length)
        {
            if (value < this.size)
            {
                throw new RuntimeException("设置的数组容易小于数组现有对像的个数");
            }
            if (value > 0)
            {
                Object[] destinationArray = new Object[value];
                if (this.size > 0)
                {
                	System.arraycopy(this.parts, 0, destinationArray, 0, this.size);
                }
                this.parts = destinationArray;
            }
            else
            {
                this.parts = new Object[4];
            }
        }
    }
	
	public SqlString toSqlString()
	{
		Object[] sqlParts = new Object[this.size];
		System.arraycopy(this.parts, 0, sqlParts, 0, this.size);
		return new SqlString(sqlParts);
	}
}
