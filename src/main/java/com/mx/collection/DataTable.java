package com.mx.collection;

import java.util.*;

/**
 * 数据表
 */
public class DataTable implements Iterable<DataRow> {
	private DataColumn[] columns;
    private DataRow[] rows;
    private int rowSize;
    private String name;
    private Map<String,Integer> colIndex;
    
    /**
     * 得到名称
     * @return
     */
    public String getName()
    {
    	return this.name;
    }
    
    /**
     * 设置名称
     * @param name
     */
    public void setName(String name)
    {
    	this.name=name;
    }

    /**
     * 得到列集合
     * @return
     */
    public DataColumn[] getColumns()
    {
        return this.columns;
    }

    /**
     * 通过行索引得到行对像
     * @param rowIndex
     * @return
     */
    public DataRow getRow(int rowIndex)
    {
        if (rowIndex > -1 && rowIndex < this.rowSize)
        {
            return this.rows[rowIndex];
        }
        else return null;
    }
    
    /**
     * 得到行对像集合
     * @return
     */
    public DataRow[] getRows()
    {
    	DataRow[] rows = new DataRow[this.rowSize];
        for (int i = 0; i < this.rowSize; i++)
        {
            rows[i] = this.rows[i];
        }
        return rows;
    }
    
    public DataTable()
    {
    	this("");
    }

    public DataTable(String name)
    {
        this.rows = new DataRow[0];
        this.columns = new DataColumn[0];
        this.rowSize = 0;
        this.name=name;
        this.colIndex=new HashMap<String,Integer>();
    }

    /**
     * 添加一列
     * @param columnName
     */
    public void addColumn(String columnName)
    {
        if (!this.contains(columnName))
        {
            DataColumn column = new DataColumn(columnName,this.getCapacity());
            column.setTable(this);
            this.addColumn(column);
        }
    }

    /**
     * 创建一新行
     * @return
     */
    public DataRow newRow()
    {
        if (this.rowSize == this.rows.length)
        {
			this.ensureCapacity(this.rowSize + 1);
        }
        DataRow row= new DataRow(this.rowSize);
        row.setTable(this);
        this.rows[this.rowSize] = row;
        this.rowSize++;
        return row;
    }

    /**
     * 添加一列
     * @param column
     * @return
     */
    public int addColumn(DataColumn column)
    {
        int index = this.columns.length;
        DataColumn[] cols = new DataColumn[index+1];
        System.arraycopy(this.columns,0,cols,0,index);
        cols[index] = column;
        this.colIndex.put(column.getName(), new Integer(index));
        this.columns = cols;
        return index;
    }

    /**
     * 通过列名获取列索引
     * @param columnName
     * @return
     */
    private int indexOf(String columnName)
    {
        if(this.colIndex.containsKey(columnName)){
        	return this.colIndex.get(columnName).intValue();
        }
        return -1;
    }

    /**
     * 是否包含列
     * @param columnName
     * @return
     */
    public boolean contains(String columnName)
    {
        return this.indexOf(columnName) != -1;
    }

    /**
     * 通过列索引得到列
     * @param columnIndex
     * @return
     */
    public DataColumn getColumn(int columnIndex)
    {
        if (columnIndex > -1 && columnIndex < this.getColumns().length)
        {
            return this.getColumns()[columnIndex];
        }
        return null;
    }

    /**
     * 通过列名得到列
     * @param columnName
     * @return
     */
    public DataColumn getColumn(String columnName)
    {
        int colIndex = this.indexOf(columnName);
        if (colIndex != -1)
        {
            return this.getColumns()[colIndex];
        }
        return null;
    }

    /**
     * 清除所有行和列
     */
    public void clear()
    {
        for (DataColumn column : this.getColumns())
        {
            column.setValues(new Object[0]);
        }
        this.rows = new DataRow[0];
        this.rowSize = 0;
    }

    /**
     * 确保行容量
     * @param min
     */
    private void ensureCapacity(int min)
    {
        if (this.rows.length < min)
        {
            int num = (this.rows.length == 0) ? 4 : (this.rows.length * 2);
            if (num < min)
            {
                num = min;
            }
            this.setCapacity(num);
        }
    }

    /**
     * 得到行容量
     * @return
     */
    public int getCapacity()
    {
        return this.rows.length;
    }

    /**
     * 设置行容量
     * @param value
     */
    public void setCapacity(int value)
    {
        if (value != this.rows.length)
        {
            if (value < this.rowSize)
            {
                throw new RuntimeException("设置的数组容易小于数组现有对像的个数");
            }
            if (value > 0)
            {
                DataRow[] destinationArray = new DataRow[value];
                if (this.rowSize > 0)
                {
                	System.arraycopy(this.rows, 0, destinationArray, 0, this.rowSize);
                }
                this.rows = destinationArray;

                for(DataColumn column : this.getColumns())
                {
                	Object[] valueArray = new Object[value];
                    if (this.rowSize > 0)
                    {
                    	System.arraycopy(column.getValues(), 0, valueArray, 0, this.rowSize);
                    }
                    column.setValues(valueArray);
                }
            }
            else
            {
                this.rows = new DataRow[4];

                for (DataColumn column : this.getColumns())
                {
                    column.setValues(new Object[4]);
                }
            }
        }
    }
    
    /**
     * 根据行索号默认排序
     */
    public void sort()
    {
    	this.sort(null);
    }
    
    /**
     * 根据行对像比较接口进行排序
     * @param comparer
     */
    public void sort(Comparator<DataRow> comparer)
    {
    	List<DataRow> list = new ArrayList<DataRow>();
    	list.addAll(Arrays.asList(this.getRows()));
    	if(comparer==null)Collections.sort(list);
    	else Collections.sort(list,comparer);
        
        for(int i = 0; i < list.size(); i++)
        {
            this.rows[i] = list.get(i);
        }
    }

    /**
     * 迭代器
     * @return
     */
	@Override
	public Iterator<DataRow> iterator() {
		return new GeneticIterator<DataRow>(this.rows,this.rowSize);
	}

	/**
	 * 列大小
	 * @return
	 */
	public int colSize() {
		return this.columns.length;
	}

	/**
	 * 行大小
	 * @return
	 */
	public int rowSize() {
		return this.rowSize;
	}
	
	public List<Map<String,Object>> toMapList(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < this.rowSize; i++){
			list.add(this.rows[i].toMap());
        }
		return list;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<rowSize;i++){
			DataRow row = this.rows[i];
			sb.append(row.toString()+"\r\n");
		}
		return sb.toString();
	}
}
