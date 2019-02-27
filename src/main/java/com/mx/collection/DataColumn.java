package com.mx.collection;

public class DataColumn {
	Object[] values;
    private String name;
	protected DataTable table;

    protected DataColumn() {
	}

	/**
     * 得到值的数组
     * @return
     */
    public Object[] getValues()
    {
        return this.values;
    }

    /**
     * 设置值的数组
     */
    public void setValues(Object[] values)
    {
        this.values = values;
    }

    /**
     * 得到列名
     * @return
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * 得到值数组的的大小
     * @return
     */
    public int size()
    {
        return this.getValues().length;
    }

    /**
     * 得到表对像
     * @return
     */
    public DataTable getTable()
    {
        return this.table;
    }

    /**
     * 设置表对像
     * @param table
     */
    public void setTable(DataTable table)
    {
        this.table = table;
    }

    /**
     * 通过行索引得到值
     * @param index
     * @return
     */
    public Object get(int index)
    {
        if (index > -1 && index < this.getTable().rowSize())
        {
            return this.getValues()[index];
        }
        return null;
    }
    
    /**
     * 通过行索引设置值
     */
    public void set(int index,Object value)
    {
        if (index > -1 && index < this.getTable().rowSize())
        {
            this.getValues()[index] = value;
        }
    }

    public DataColumn(String columnName,int capacity)
    {
        this.name = columnName;
        this.values = new Object[capacity];
    }
}
