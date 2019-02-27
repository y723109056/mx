package com.mx.sql.mapping.asserts;

import com.mx.sql.SqlMapException;
import com.mx.sql.dialect.IDialect;
import com.mx.sql.mapping.ISqlClauseAssert;
import com.mx.util.StringUtil;
import com.mx.util.TypeUtil;

/**
 * 判断是否大于
 */
public class IsGreater implements ISqlClauseAssert {

	@Override
	public String getName() {
		return "isGreater";
	}

	@Override
	public boolean invoke(IDialect dialect,Object... args) {
		if(args.length>1)
		{
			Object arg1 = args[0];
			Object arg2 = args[1];
			return TypeUtil.compareTo(arg1, arg2)==1;
		}
		else throw new SqlMapException(StringUtil.format("断言 {0} 缺少参数  {1}", this.getName(), args.length));
	}

}
