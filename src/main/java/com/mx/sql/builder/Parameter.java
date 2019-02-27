package com.mx.sql.builder;

/**
 * Sql语句参数对像,拼接Sql语句时的，参数对像
 */
public class Parameter
{
	/**
	 * 参数的值
	 */
	private Object value;
	private Class<?> type;
	
	protected Parameter() {
	}

	/**
	 * 得到参数的值
	 * @return
	 */
	public Object getValue(){
		return this.value;
	}
	
	public void setValue(Object value){
		this.value = value;
		if(value!=null)this.type=value.getClass();
	}
	
	public Class<?> getType(){
		return this.type;
	}
	
    public Parameter(Object value){
    	this.value = value;
    	if(value!=null)this.type=value.getClass();
    }
    
    public Parameter(Object value,Class<?> type){
    	this.value = value;
    	this.type = type;
    }

    /**
     * 输出参数占位符
     */
    @Override
    public String toString(){
        return "?";
    }
}
