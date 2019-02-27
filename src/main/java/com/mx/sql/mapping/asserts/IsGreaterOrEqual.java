package com.mx.sql.mapping.asserts;


import com.mx.sql.SqlMapException;
import com.mx.sql.dialect.IDialect;
import com.mx.sql.mapping.ISqlClauseAssert;
import com.mx.util.StringUtil;

/**
 * 判断是否大于或等于
 */
public class IsGreaterOrEqual implements ISqlClauseAssert {

	@Override
	public String getName() {
		return "isGreaterOrEqual";
	}

	@Override
	public boolean invoke(IDialect dialect,Object... args) {
		if(args.length>1)
		{
			return false;
		}
		else throw new SqlMapException(StringUtil.format("断言 {0} 缺少参数  {1}", this.getName(), args.length));
	}

}
