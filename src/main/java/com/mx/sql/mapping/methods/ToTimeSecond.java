package com.mx.sql.mapping.methods;

import com.mx.sql.builder.Parameter;
import com.mx.sql.builder.SqlString;
import com.mx.sql.config.IDbConfig;
import com.mx.sql.dialect.IDialect;
import com.mx.sql.mapping.ISqlMethod;
import com.mx.util.DateUtil;
import com.mx.util.TypeUtil;

import java.util.Date;


public class ToTimeSecond implements ISqlMethod {

	@Override
	public String getName() {
		return "toTimeSecond";
	}

	@Override
	public SqlString invoke(IDbConfig config,IDialect dialect,Object... args) {
		if(args.length>0)
		{
			Object arg = args[0];
			if(arg!=null)
			{
				boolean paramFlag = arg instanceof Parameter;
				Object date = TypeUtil.changeType((paramFlag) ? ((Parameter) arg).getValue() : arg, Date.class);
				if(date instanceof Date)
				{
					Integer t = DateUtil.getTimeTotalSecond((Date) date);
					if(paramFlag)
					{
						return new SqlString(new Parameter(t));
					}
					else
					{
						return new SqlString(t.toString());
					}
				}
			}
		}
		return null;
	}
}