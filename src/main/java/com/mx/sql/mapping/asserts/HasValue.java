package com.mx.sql.mapping.asserts;

import com.mx.sql.SqlMapException;
import com.mx.sql.dialect.IDialect;
import com.mx.sql.mapping.ISqlClauseAssert;
import com.mx.util.StringUtil;
import com.mx.util.TypeUtil;

import java.util.Collection;

public class HasValue implements ISqlClauseAssert {

    @Override
    public String getName() {
        return "hasValue";
    }

    @Override
    public boolean invoke(IDialect dialect,Object... args) {
        if(args.length>1){
            Object obj = args[0];
            if(obj!=null){
                for(int i=1;i<args.length;i++){
                    if(TypeUtil.isValue(obj)){
                        if(obj.equals(args[i]))return true;
                    }else if(TypeUtil.isArray(obj)){
                        for(Object v : (Object[]) obj){
                            if(v!=null && v.equals(args[i]))return true;
                        }
                    }else if(TypeUtil.isCollection(obj)){
                        for(Object v : (Collection<?>) obj){
                            if(v!=null && v.equals(args[i]))return true;
                        }
                    }
                    if(args[0].equals(args[i]))return true;
                }
            }
            return false;
        }
        else throw new SqlMapException(StringUtil.format("断言 {0} 缺少参数  {1}", this.getName(), args.length));
    }

}
